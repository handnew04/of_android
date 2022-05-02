package kr.onekey.of.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kr.onekey.of.R

class EmailValidCheckEditText : AppCompatEditText {
   constructor(context: Context) : super(context)
   constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
   constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
      context,
      attrs,
      defStyleAttr
   )

   init {
      doAfterTextChanged {
         it?.let {
            backgroundTintList = if (isEmailValid(it)) {
               ColorStateList.valueOf(resources.getColor(R.color.green_500, context.theme))
            } else {
               ColorStateList.valueOf(resources.getColor(R.color.gray_600, context.theme))
            }
         }
      }
   }

   private fun isEmailValid(email: CharSequence) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}