package kr.co.ollefarm.ui

import android.app.AlertDialog
import kr.co.ollefarm.R
import kr.co.ollefarm.databinding.DialogChoiceBinding

class PictureDialog(
   binding: DialogChoiceBinding,
   cameraButtonClickListener: DialogOnClickListener,
   galleryButtonClickListener: DialogOnClickListener
) {
   private val dialog =
      AlertDialog.Builder(binding.root.context, R.style.CustomAlertDialog)
         .setView(binding.root)
         .create()

   init {
      binding.apply {
         tvBack.setOnClickListener {
            dialog.dismiss()
         }
         tvCamera.setOnClickListener {
            cameraButtonClickListener.onClick()
         }
         tvGallery.setOnClickListener {
            galleryButtonClickListener.onClick()
         }
      }
   }


   fun show() {
      dialog.show()
   }

   fun dismiss() {
      dialog.dismiss()
   }

   interface DialogOnClickListener {
      fun onClick()
   }
}