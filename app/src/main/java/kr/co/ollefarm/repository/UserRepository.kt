package kr.co.ollefarm.repository

import kr.co.ollefarm.base.BaseRepository
import kr.co.ollefarm.model.UserInfo
import kr.co.ollefarm.network.ApiHandler
import kr.co.ollefarm.network.api.UserApi

class UserRepository(private val userApi: UserApi) : BaseRepository() {
   fun uploadProfileImage() {

   }

   suspend fun updateUser(userId: Int, userInfo: UserInfo) =
      ApiHandler().apiCall { userApi.updateUser(userId, userInfo) }

   suspend fun getUserInfo(userId: Int) = ApiHandler().apiCall { userApi.getUser(userId) }
}