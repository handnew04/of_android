package kr.co.ollefarm.model

import com.google.gson.annotations.SerializedName

data class PhoneInfo(
   @SerializedName("fcmId")
   val fcmId: String,
   @SerializedName("appVersion")
   val appVersion: String,
   @SerializedName("model")
   val model: String,
   @SerializedName("os")
   val os: String,
   @SerializedName("osVersion")
   val osVersion: String
)