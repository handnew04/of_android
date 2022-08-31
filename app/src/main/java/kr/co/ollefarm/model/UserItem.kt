package kr.co.ollefarm.model

import com.google.gson.annotations.SerializedName
import kr.co.ollefarm.base.BaseResponse
import retrofit2.http.Url

data class UserItem(
    @SerializedName("userId")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val profileImage: Url?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("email")
    val email: String
) : BaseResponse