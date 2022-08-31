package kr.co.ollefarm.network.api

import kr.co.ollefarm.model.TokenInfo
import retrofit2.Response
import retrofit2.http.POST

interface AuthApi {
   @POST("auth/refresh")
   suspend fun getNewToken() : Response<TokenInfo>
}