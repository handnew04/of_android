package kr.onekey.of.network.api

import kr.onekey.of.model.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
   @GET("user/{id}")
   suspend fun getUser(@Path("id") userId: Int) : Response<UserInfo>
}