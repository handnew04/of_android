package kr.onekey.of.repository

import kr.onekey.of.base.BaseRepository
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.api.UserApi

class UserRepository(private val userApi: UserApi) : BaseRepository() {
   fun uploadProfileImage() {

   }

   fun updateUserName() {

   }

   fun updateUserPhoneNumber() {

   }

   suspend fun getUserInfo(userId: String) = ApiHandler().apiCall { userApi.getUser(userId) }
}