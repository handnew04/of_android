package kr.onekey.of.repository

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kr.onekey.of.base.BaseRepository
import kr.onekey.of.model.LoginItem
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.network.api.LoginApi
import kr.onekey.of.network.convertBoolResult
import kr.onekey.of.util.PrefUtil

class LoginRepository(private val loginApi: LoginApi, private val prefUtil: PrefUtil) :
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
      FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
         if (!task.isSuccessful) {
            Log.w("Application", "Fetching FCM registration failed", task.exception)
            return@addOnCompleteListener
         }

         val token = task.result
         Log.e("FCMToken", token)


      }
   }

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