package kr.co.ollefarm.network.api

import kr.co.ollefarm.model.LoginItem
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("passwordHash") password: String
    ): Response<LoginItem>
}