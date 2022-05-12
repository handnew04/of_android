package kr.onekey.of.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class UserInfo(
    @SerializedName("name")
    val userName: String,
    @SerializedName("image")
    val profileImage: Url?,
    @SerializedName("phone")
    val userPhoneNumber: String?,
    @SerializedName("email")
    val userEmail: String
) {
}