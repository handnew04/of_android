package kr.onekey.of.repository

import android.os.Build
import android.system.Os
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.onekey.of.BuildConfig
import kr.onekey.of.base.BaseRepository
import kr.onekey.of.model.LoginItem
import kr.onekey.of.model.PhoneInfo
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.network.api.LoginApi
import kr.onekey.of.network.api.UserApi
import kr.onekey.of.network.convertBoolResult
import kr.onekey.of.util.PrefUtil
import kotlin.coroutines.CoroutineContext

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
      name = "이름이뭘까",
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