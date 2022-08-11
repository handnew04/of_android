package kr.onekey.of.ui.main

import android.util.Log
import kr.onekey.of.BuildConfig
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.util.PrefUtil

class MainViewModel(private val prefUtil: PrefUtil) : BaseViewModel() {
   fun getToken(): Map<String, String> {
      val map = HashMap<String, String>()
      map["access_token"] = prefUtil.getAccessToken() ?: "NotFoundAccessToken"
      map["refresh_token"] = prefUtil.getRefreshToken() ?: "NotFoundRefreshToken"

      Log.e(TAG, "Token: a = ${map["access_token"]} // r = ${map["refresh_token"]}")
      return map
   }

   fun makeTokenUrl(): String {
      return BuildConfig.WEB_URL + "/splash/${prefUtil.getAccessToken()}"
   }

   fun updateTokens(accessToken: String, refreshToken: String) {
      Log.e(TAG, "updateToken! access: ${accessToken}, refresh: ${refreshToken}")
      prefUtil.saveLoginTokens(accessToken, refreshToken)
   }
}