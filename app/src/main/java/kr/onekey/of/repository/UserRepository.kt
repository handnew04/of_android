package kr.onekey.of.repository

import kr.onekey.of.base.BaseRepository
import kr.onekey.of.model.PhoneInfo
import kr.onekey.of.model.UserInfo
import kr.onekey.of.network.ApiHandler
import kr.onekey.of.network.api.UserApi

class UserRepository(private val userApi: UserApi) : BaseRepository() {
   fun uploadProfileImage() {

   }

   suspend fun updatePhone(userId: Int, phoneInfo: PhoneInfo) =
      ApiHandler().apiCall { userApi.updatePhone(userId, phoneInfo) }

   suspend fun updateUser(userId: Int, userInfo: UserInfo) =
      ApiHandler().apiCall { userApi.updateUser(userId, userInfo) }

   suspend fun getUserInfo(userId: Int) = ApiHandler().apiCall { userApi.getUser(userId) }
}