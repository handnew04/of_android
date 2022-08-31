package kr.co.ollefarm.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
   @SerializedName("name")
   val name: String,
   @SerializedName("phone")
   val phoneNumber: String
)