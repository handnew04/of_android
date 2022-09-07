package kr.co.ollefarm.ui.set

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import kr.co.ollefarm.R
import kr.co.ollefarm.base.BaseActivity
import kr.co.ollefarm.databinding.ActivitySettingBinding
import kr.co.ollefarm.databinding.DialogChoiceBinding
import kr.co.ollefarm.ui.PictureDialog
import kr.co.ollefarm.util.PermissionUtil
import kr.co.ollefarm.util.PictureUtil
import kr.co.ollefarm.util.PictureUtil.PERMISSION_REQUEST_CODE_CAMERA
import kr.co.ollefarm.util.PictureUtil.PERMISSION_REQUEST_CODE_GALLERY
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity :
   BaseActivity<ActivitySettingBinding, SettingViewModel>(R.layout.activity_setting) {
   override val viewModel: SettingViewModel by viewModel()
   private lateinit var resultLauncher: ActivityResultLauncher<Intent>

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      resultLauncher = initResultLauncher()

      setListeners()
      observe()
      viewModel.getUser()
   }

   private fun observe() {
      viewModel.isCompletedUpdatingUserInfo.observe(this) { isCompleted ->
         if (isCompleted) {
            Toast.makeText(this@SettingActivity, "회원님의 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
         } else {
            Toast.makeText(this@SettingActivity, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
         }
      }
   }

   private fun setListeners() {
      binding.apply {
         ivProfile.setOnClickListener {
            //showDialog()
            // TODO: 수정 예정
            Toast.makeText(this@SettingActivity, "준비 중 입니다.", Toast.LENGTH_SHORT).show()
         }
         tvSave.setOnClickListener {

         }
         tvCancel.setOnClickListener {
            finish()
         }
      }
   }

   private fun showDialog() {
      val dialogBinding: DialogChoiceBinding =
         DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_choice, null, false)

      PictureDialog(
         dialogBinding,
         cameraButtonClickListener = object : PictureDialog.DialogOnClickListener {
            override fun onClick() {
               takePicture()
            }

         },
         galleryButtonClickListener = object : PictureDialog.DialogOnClickListener {
            override fun onClick() {
               getPicture()
            }
         }).show()
   }

   private fun takePicture() {
      checkPermissions(PERMISSION_REQUEST_CODE_CAMERA) {

      }
   }

   private fun getPicture() {
      checkPermissions(PERMISSION_REQUEST_CODE_GALLERY) {
         val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         resultLauncher.launch(intent)
      }
   }

   private fun checkPermissions(requestCode: Int, doFunc: () -> Unit) {
      when (requestCode) {
         PERMISSION_REQUEST_CODE_GALLERY -> {
            if (PermissionUtil.hasPermissions(this, PictureUtil.getGalleryPermissions())) {
               doFunc()
            } else {
               PictureUtil.requestPermissionsForGallery(this)
            }
         }
         PERMISSION_REQUEST_CODE_CAMERA -> {
            if (PermissionUtil.hasPermissions(this, PictureUtil.getCameraPermissions())) {
               doFunc()
            } else {
               PictureUtil.requestPermissionForCamera(this)
            }
         }
         else -> {

         }
      }
   }

   private fun initResultLauncher() =
      registerForActivityResult(
         ActivityResultContracts.StartActivityForResult()
      ) { result ->
         if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { imageUri ->
               val filePath = getFilePath(imageUri)
               Log.e("Photo ", "photo uri : $imageUri")
               viewModel.uploadPicture(filePath!!)
            }
         } else {

         }
      }

   private fun getFilePath(uri: Uri): String? {
      val cursor = contentResolver.query(uri, null, null, null, null)
      var path: String? = null
      cursor?.let {
         cursor.moveToNext()
         path = cursor.getString(cursor.getColumnIndexOrThrow("_data"))
         cursor.close()
      }
      return path
   }

   override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<out String>,
      grantResults: IntArray
   ) {

      if (grantResults.any { result -> result == PackageManager.PERMISSION_DENIED }) {
         Toast.makeText(this@SettingActivity, "권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
         showDetailSettings()
         return
      }

      if (requestCode == PERMISSION_REQUEST_CODE_GALLERY) {
         getPicture()
      } else if (requestCode == PERMISSION_REQUEST_CODE_CAMERA) {
         takePicture()
      }
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
   }

   private fun showDetailSettings() {
      val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
      val uri = Uri.fromParts("package", packageName, null)
      intent.data = uri
      startActivity(intent)
   }
}