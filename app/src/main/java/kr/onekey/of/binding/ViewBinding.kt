package kr.onekey.of.binding

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.http.Url

object ViewBinding {

   @JvmStatic
   @BindingAdapter("profileImage")
   fun bindProfileImage(view: CircleImageView, url: Url?) {
      url?.let {
         Glide.with(view.context)
            .load(url)
            .into(view)
      }
   }
}