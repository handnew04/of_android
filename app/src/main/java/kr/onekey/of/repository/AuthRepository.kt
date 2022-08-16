package kr.onekey.of.repository

import android.util.Log
import kotlinx.coroutines.runBlocking
import kr.onekey.of.base.BaseRepository
import kr.onekey.of.model.TokenInfo
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.ApiHandler.Companion.SUCCESS_CODE
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.network.api.AuthApi
import kr.onekey.of.network.exception.ApiBaseException.Companion.INVALID_TOKEN_EXCEPTION_CODE
import kr.onekey.of.util.PrefUtil

class AuthRepository(private val authApi: AuthApi, private val prefUtil: PrefUtil) :
   BaseRepository() {

   fun requestAccessToken(): TokenInfo {

      val tokenInfo = runBlocking {
         prefUtil.changeRefreshTokenToAccessToken()

         when (val response = ApiHandler().apiCall {
            authApi.getNewToken()
         }) {
            is ResultWrapper.Success -> {
               val value = response.value
               Log.d(TAG, "response success!")
               TokenInfo(SUCCESS_CODE, value.accessToken!!, value.refreshToken!!)
            }
            is ResultWrapper.Error -> {
               Log.d(TAG, "response error!")
               TokenInfo(INVALID_TOKEN_EXCEPTION_CODE, null, null)
            }
         }
      }

      return tokenInfo
   }

   fun getAccessToken() = prefUtil.getAccessToken()

   fun saveTokens(accessToken: String, refreshToken: String) {
      prefUtil.saveLoginTokens(accessToken, refreshToken)
   }
}