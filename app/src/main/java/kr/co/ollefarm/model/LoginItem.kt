package kr.co.ollefarm.model

import com.google.gson.annotations.SerializedName
import kr.co.ollefarm.base.BaseResponse

data class LoginItem(
   @SerializedName("userId")
   val userId: Int,
   @SerializedName("accessToken")
   val accessToken: String,
   @SerializedName("refreshToken")
   val refreshToken: String
) : BaseResponse {
}