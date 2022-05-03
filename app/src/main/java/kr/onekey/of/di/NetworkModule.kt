package kr.onekey.of.di

import kr.onekey.of.BuildConfig
import kr.onekey.of.BuildConfig.BASE_URL
import kr.onekey.of.network.api.LoginApi
import kr.onekey.of.network.api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
   single { createRetrofit() }
   single { initOkHttpClient() }

   single<LoginApi> { get<Retrofit>().create(LoginApi::class.java) }
   single<UserApi> { get<Retrofit>().create(UserApi::class.java) }
}

private fun Scope.createRetrofit() = Retrofit.Builder()
   .baseUrl(BASE_URL)
   .addConverterFactory(GsonConverterFactory.create())
   .client(get())
   .build()

const val TIME_OUT = 50000L

private fun initOkHttpClient() = OkHttpClient.Builder()
   .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
   .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
   .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
   .addInterceptor(HttpLoggingInterceptor().apply {
      level = if (BuildConfig.DEBUG) {
         HttpLoggingInterceptor.Level.BODY
      } else {
         HttpLoggingInterceptor.Level.NONE
      }
   })
   .build()