package kr.co.ollefarm.network.api

import kr.co.ollefarm.model.PhoneInfo
import kr.co.ollefarm.model.UserInfo
import kr.co.ollefarm.model.UserItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
   @GET("user/{id}")
   suspend fun getUser(@Path("id") userId: Int): Response<UserItem>

   @PUT("user/{id}")
   suspend fun updateUser(@Path("id") userId: Int, @Body userInfo: UserInfo): Response<ResponseBody>

   @PUT("user/{id}/phone")
   suspend fun updatePhone(
      @Path("id") userId: Int,
      @Body phoneInfo: PhoneInfo
   ): Response<ResponseBody>
}