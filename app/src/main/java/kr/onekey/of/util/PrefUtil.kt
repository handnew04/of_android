package kr.onekey.of.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PrefUtil(context: Context) {
   companion object {
      const val DEFAULT_VALUE = ""
      const val PREF_DEFAULT = "PREF_DEFAULT"
      const val LOGIN_TOKEN = "LOGIN_TOKEN"
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

   fun saveId(userId: String) {
      getEdit().putString(USER_ID, userId).apply()
   }

   fun getId() = sharedPref.getString(USER_ID, DEFAULT_VALUE)

   fun savePassword(password: String) {
      getEdit().putString(USER_PASSWORD, password).apply()
   }

   fun getPassword() = sharedPref.getString(USER_PASSWORD, DEFAULT_VALUE)

   fun initLogin() {
      getEdit().remove(USER_PASSWORD).apply()
   }
}