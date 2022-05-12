package kr.onekey.of.network.api

import kr.onekey.of.model.UserInfo
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.POST

interface LoginApi {
    @POST("user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("passwordHash") password: String
    ): Response<UserInfo>
}