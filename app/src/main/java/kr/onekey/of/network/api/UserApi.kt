package kr.onekey.of.network.api

import kr.onekey.of.model.UserInfo
import kr.onekey.of.model.UserItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
   @GET("user/{id}")
   suspend fun getUser(@Path("id") userId: Int): Response<UserItem>

   @PUT("user/{id}")
   suspend fun updateUser(@Path("id") userId: Int, @Body userInfo: UserInfo): Response<ResponseBody>

}