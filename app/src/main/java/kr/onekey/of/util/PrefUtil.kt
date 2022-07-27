package kr.onekey.of.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PrefUtil(context: Context) {
   companion object {
      const val DEFAULT_VALUE = ""
      const val DEFAULT_INT_VALUE = 0
      const val PREF_DEFAULT = "PREF_DEFAULT"
      const val ACCESS_TOKEN = "ACCESS_TOKEN"
      const val REFRESH_TOKEN = "REFRESH_TOKEN"
      const val USER_ID = "USER_ID" //server user key
      const val USER_EMAIL = "USER_EMAIL" //login id
      const val USER_PASSWORD = "USER_PASSWORD"
      const val USER_NAME = "USER_NAME"
      const val FCM_TOKEN = "FCM_TOKEN"
   }

   private val sharedPref = context.getSharedPreferences(PREF_DEFAULT, MODE_PRIVATE)
   private fun getEdit() = sharedPref.edit()

   fun saveEmail(email: String) {
      getEdit().putString(USER_EMAIL, email).apply()
   }

   fun getEmail() = sharedPref.getString(USER_EMAIL, DEFAULT_VALUE)

   fun saveId(userId: Int) {
      getEdit().putInt(USER_ID, userId).apply()
   }

   fun getId() = sharedPref.getInt(USER_ID, DEFAULT_INT_VALUE)

   fun savePassword(password: String) {
      getEdit().putString(USER_PASSWORD, password).apply()
   }

   fun getPassword() = sharedPref.getString(USER_PASSWORD, DEFAULT_VALUE)

   fun initLogin() {
      getEdit().remove(USER_PASSWORD).apply()
   }

   fun saveLoginTokens(accessToken: String, refreshToken: String) {
      getEdit().apply {
         putString(ACCESS_TOKEN, accessToken)
         putString(REFRESH_TOKEN, refreshToken)
      }.apply()
   }

   fun saveAccessToken(accessToken: String) {
      getEdit().putString(ACCESS_TOKEN, DEFAULT_VALUE).apply()
   }

   fun changeRefreshTokenToAccessToken(): Boolean {
      getRefreshToken()?.let { refreshToken ->
         saveAccessToken(refreshToken)
         return true
      }
      return false
   }

   fun getAccessToken() = sharedPref.getString(ACCESS_TOKEN, DEFAULT_VALUE)

   fun getRefreshToken() = sharedPref.getString(REFRESH_TOKEN, DEFAULT_VALUE)
}