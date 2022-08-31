package kr.co.ollefarm.network.interceptor

import android.content.Context
import kr.co.ollefarm.OFApplication
import kr.co.ollefarm.network.exception.ApiBaseException.Companion.INVALID_TOKEN_EXCEPTION_CODE
import kr.co.ollefarm.repository.AuthRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthenticationInterceptor(private val context: Context) : Interceptor, KoinComponent {
   private val authRepository: AuthRepository by inject()

   override fun intercept(chain: Interceptor.Chain): Response {
      val api = chain.request().url.toString().split("api/")[1]
      val accessToken = authRepository.getAccessToken()

      return if (api != LOGIN_API) {
         val newRequest = chain.request().appendToken(accessToken)
         val response = chain.proceed(newRequest)

         if (response.isTokenInvalid()) {
            if (api == REQUEST_NEW_TOKEN) {
               (context as OFApplication).showLoginActivity()
               response.close()
               return response
            }

            val refreshResponse = authRepository.requestAccessToken()

            val newAccessToken = refreshResponse.accessToken
            val newRefreshToken = refreshResponse.refreshToken

            if (newAccessToken != null && newRefreshToken != null) {
               authRepository.saveTokens(newAccessToken, newRefreshToken)
               val refreshRequest = chain.request().appendToken(newAccessToken)
               return chain.proceed(refreshRequest)
            }
         }

         return response
      } else {
         chain.proceed(chain.request())
      }
   }

   private fun Request.appendToken(token: String): Request {
      return this.newBuilder()
         .addHeader(
            AUTHORIZATION,
            "Bearer $token"
         )
         .build()
   }

   private fun Response.isTokenInvalid(): Boolean {
      return this.code == INVALID_TOKEN_EXCEPTION_CODE
   }

   companion object {
      const val LOGIN_API = "auth/login"
      const val REQUEST_NEW_TOKEN = "auth/refresh"
      const val AUTHORIZATION = "Authorization"
      const val TAG = "AuthenticationInterceptor"
   }
}