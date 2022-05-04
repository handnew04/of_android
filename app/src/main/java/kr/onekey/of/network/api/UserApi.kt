package kr.onekey.of.network.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
   @GET("user")
   suspend fun getUser() : Response<ResponseBody>
}