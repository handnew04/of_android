package kr.onekey.of.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class UserInfo(
   @SerializedName("userName")
   val userName: String,
   @SerializedName("image")
   val profileImage: Url,
   @SerializedName("phoneNumber")
   val userPhoneNumber: String,
   @SerializedName("userEmail")
   val userEmail: String
) {
}