package kr.onekey.of.di

import kr.onekey.of.BuildConfig
import kr.onekey.of.BuildConfig.BASE_URL
import kr.onekey.of.network.api.AuthApi
import kr.onekey.of.network.api.LoginApi
import kr.onekey.of.network.api.UserApi
import kr.onekey.of.network.interceptor.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val DI_CLIENT = "DI_CLIENT"

val networkModule = module {
   single { createRetrofit() }

   single(named(DI_CLIENT)) { OkHttpClient.Builder()
      .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
      .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
      .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
      .addInterceptor(AuthenticationInterceptor(androidContext()))
      .addInterceptor(HttpLoggingInterceptor().apply {
         level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
         } else {
            HttpLoggingInterceptor.Level.NONE
         }
      })
      .build()
   }

   single<LoginApi> { get<Retrofit>().create(LoginApi::class.java) }
   single<UserApi> { get<Retrofit>().create(UserApi::class.java) }
   single<AuthApi> { get<Retrofit>().create(AuthApi::class.java) }
}

private fun Scope.createRetrofit() = Retrofit.Builder()
   .baseUrl(BASE_URL)
   .addConverterFactory(GsonConverterFactory.create())
   .client(get(named(DI_CLIENT)))
   .build()

const val TIME_OUT = 50000L