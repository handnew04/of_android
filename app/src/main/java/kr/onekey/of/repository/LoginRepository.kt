package kr.onekey.of.repository

import kr.onekey.of.base.BaseRepository
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.api.LoginApi
import kr.onekey.of.util.PrefUtil

class LoginRepository(private val loginApi: LoginApi, private val prefUtil: PrefUtil) :
   BaseRepository() {
   suspend fun login(id: String, pw: String) = ApiHandler().apiCall { loginApi.login(id, pw) }
   fun savePassword(password: String) {
      prefUtil.savePassword(password)
   }

   fun initLogin() {
      prefUtil.initLogin()
   }

   fun saveEmail(email: String) {
      prefUtil.saveEmail(email)
   }

   fun saveId(id: String) {
      prefUtil.saveId(id)
   }
}