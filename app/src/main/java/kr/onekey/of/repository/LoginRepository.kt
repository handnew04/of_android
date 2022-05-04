package kr.onekey.of.repository

import kr.onekey.of.base.BaseRepository
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.api.UserApi

class LoginRepository(private val userApi: UserApi) : BaseRepository() {
   suspend fun test() = ApiHandler().apiCall { userApi.getUser() }
}