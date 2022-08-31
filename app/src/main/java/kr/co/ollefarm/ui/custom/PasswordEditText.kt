package kr.co.ollefarm.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import kr.co.ollefarm.R

class PasswordEditText : AppCompatEditText {
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
            backgroundTintList = if (it.isBlank()) {
               ColorStateList.valueOf(resources.getColor(R.color.gray_600, context.theme))
            } else {
               ColorStateList.valueOf(resources.getColor(R.color.green_500, context.theme))
            }
         }
      }
   }
}