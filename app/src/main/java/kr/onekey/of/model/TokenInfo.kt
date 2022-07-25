package kr.onekey.of.model

import com.google.gson.annotations.SerializedName

data class TokenInfo(
   @SerializedName("userId")
   val userId: Int,
   @SerializedName("accessToken")
   val accessToken: String,
   @SerializedName("refreshToken")
   val refreshToken: String
) {
}