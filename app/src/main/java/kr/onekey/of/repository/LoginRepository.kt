package kr.onekey.of.repository

import kr.onekey.of.base.BaseRepository
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.api.LoginApi

class LoginRepository(private val loginApi: LoginApi) : BaseRepository() {
    suspend fun login(id: String, pw: String) = ApiHandler().apiCall { loginApi.login(id, pw) }
}