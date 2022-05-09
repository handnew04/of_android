package kr.onekey.of.ui.set

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import kr.onekey.of.R
import kr.onekey.of.base.BaseActivity
import kr.onekey.of.databinding.ActivitySettingBinding
import kr.onekey.of.databinding.DialogChoiceBinding
import kr.onekey.of.ui.PictureDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity :
   BaseActivity<ActivitySettingBinding, SettingViewModel>(R.layout.activity_setting) {
   override val viewModel: SettingViewModel by viewModel()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setListeners()
   }

   private fun setListeners() {
      binding.apply {
         ivProfile.setOnClickListener {
            showDialog()
         }
         tvSave.setOnClickListener {

         }
         tvCancel.setOnClickListener {

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

   }

   private fun getPicture() {

   }
}