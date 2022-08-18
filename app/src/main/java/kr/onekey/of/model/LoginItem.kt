package kr.onekey.of.model

import com.google.gson.annotations.SerializedName
import kr.onekey.of.base.BaseResponse

data class LoginItem(
   @SerializedName("userId")
   val userId: Int,
   @SerializedName("accessToken")
   val accessToken: String,
   @SerializedName("refreshToken")
   val refreshToken: String
) : BaseResponse {
}