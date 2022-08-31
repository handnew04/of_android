package kr.co.ollefarm.repository

import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.ollefarm.BuildConfig
import kr.co.ollefarm.base.BaseRepository
import kr.co.ollefarm.model.PhoneInfo
import kr.co.ollefarm.network.ApiHandler
import kr.co.ollefarm.network.ResultWrapper
import kr.co.ollefarm.network.api.LoginApi
import kr.co.ollefarm.network.api.UserApi
import kr.co.ollefarm.network.convertBoolResult
import kr.co.ollefarm.util.PrefUtil

class LoginRepository(
   private val loginApi: LoginApi,
   private val userApi: UserApi,
   private val prefUtil: PrefUtil
) :
   BaseRepository() {
   suspend fun login(id: String, pw: String): Boolean {
      val response = ApiHandler().apiCall { loginApi.login(id, pw) }

      when (response) {
         is ResultWrapper.Success -> {
            prefUtil.saveId(response.value.userId)
            prefUtil.saveLoginTokens(response.value.accessToken, response.value.refreshToken)
            updatePhoneInfo()
         }
         is ResultWrapper.Error -> {
            response.exception.printStackTrace()
         }
      }

      return response.convertBoolResult()
   }

   private fun updatePhoneInfo() {
      GlobalScope.launch {
         val fcmToken = FirebaseMessaging.getInstance().token.await()
         Log.e(TAG, "fcmToken!!!: $fcmToken")
         requestUpdate(prefUtil.getId(), makePhoneInfo(fcmToken))
      }
   }

   private suspend fun requestUpdate(userId: Int, phoneInfo: PhoneInfo) {
      when (val response = ApiHandler().apiCall { userApi.updatePhone(userId, phoneInfo) }) {
         is ResultWrapper.Error -> {
            response.exception.printStackTrace()
         }
         else -> {}
      }
   }

   private fun makePhoneInfo(fcmToken: String) = PhoneInfo(
      fcmId = fcmToken,
      appVersion = BuildConfig.VERSION_NAME,
      model = Build.MODEL,
      os = "ANDROID",
      osVersion = Build.VERSION.SDK_INT.toString()
   )


   fun savePassword(password: String) {
      prefUtil.savePassword(password)
   }

   fun initLogin() {
      prefUtil.initLogin()
   }

   fun saveEmail(email: String) {
      prefUtil.saveEmail(email)
   }
}