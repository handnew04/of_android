package kr.co.ollefarm.util

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Build
import androidx.core.app.ActivityCompat
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

object PictureUtil {
   fun getCameraPermissions() = mutableListOf(Manifest.permission.CAMERA).apply {
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
         add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
      }
   }.toTypedArray()

   fun getGalleryPermissions() = mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE).apply {
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
         add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
      }
   }.toTypedArray()

   fun requestPermissionsForGallery(activity: Activity) {
      ActivityCompat.requestPermissions(
         activity,
         getGalleryPermissions(),
         PERMISSION_REQUEST_CODE_GALLERY
      )
   }

   fun requestPermissionForCamera(activity: Activity) {
      ActivityCompat.requestPermissions(
         activity,
         getCameraPermissions(),
         PERMISSION_REQUEST_CODE_CAMERA
      )
   }

   fun getInputStream(byteArray: ByteArray) = ByteArrayInputStream(byteArray)

   fun convertToByteArray(imagePath: String): ByteArray {
      val image = BitmapFactory.decodeFile(imagePath)
      val exifInterface = ExifInterface(imagePath)
      val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
      val rotateImage = rotate(image, orientation)
      val bos = ByteArrayOutputStream()
      rotateImage.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, bos)
      return bos.toByteArray()
   }

   private fun rotate(bitmap: Bitmap, orientation: Int): Bitmap {
      val matrix = Matrix()
      matrix.postRotate(getDegrees(orientation))
      return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
   }

   private fun getDegrees(orientation: Int) : Float {
      return when(orientation) {
         ExifInterface.ORIENTATION_ROTATE_90 -> {
            90f
         }
         ExifInterface.ORIENTATION_ROTATE_180 -> {
            180f
         }
         ExifInterface.ORIENTATION_ROTATE_270 -> {
            270f
         }
         else -> {
            0f
         }
      }
   }

   const val IMAGE_QUALITY = 95
   const val PERMISSION_REQUEST_CODE_GALLERY = 101
   const val PERMISSION_REQUEST_CODE_CAMERA = 102
}