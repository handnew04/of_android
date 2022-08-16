package kr.onekey.of.network.api

import kr.onekey.of.model.TokenInfo
import retrofit2.Response
import retrofit2.http.POST

interface AuthApi {
   @POST("auth/refresh")
   suspend fun getNewToken() : Response<TokenInfo>
}