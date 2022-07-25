package kr.onekey.of.network.interceptor

import kr.onekey.of.di.DI_PREF_UTIL
import kr.onekey.of.util.PrefUtil
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class AuthenticationInterceptor : Interceptor, KoinComponent {
   private val prefUtil: PrefUtil by inject(named(DI_PREF_UTIL))

   override fun intercept(chain: Interceptor.Chain): Response {
      val api = chain.request().url.toString().split("api/")[1]
      return if (api != LOGIN_API) {
         getRequestWithAuthorizationToken(chain)
      } else {
         chain.proceed(chain.request())
      }
   }

   private fun getRequestWithAuthorizationToken(chain: Interceptor.Chain): Response {
      val token = prefUtil.getAccessToken()

      val request = chain.request().newBuilder()
         .addHeader(
            "Authorization",
            "Bearer $token"
         )
         .build()

      return chain.proceed(request)
   }

   companion object {
      const val LOGIN_API = "auth/login"
      const val TAG = "AuthenticationInterceptor"
   }
}