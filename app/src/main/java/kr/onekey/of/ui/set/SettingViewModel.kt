package kr.onekey.of.ui.set

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.model.UserInfo
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.repository.UserRepository
import kr.onekey.of.util.PrefUtil

class SettingViewModel(private val prefUtil: PrefUtil, private val userRepository: UserRepository) :
   BaseViewModel() {
   val userInfo = MutableLiveData<UserInfo>()

   fun getUser() {
      launch {
         val userId = prefUtil.getId()

         userId?.let {
            val response = userRepository.getUserInfo(userId)

            withContext(Dispatchers.Main) {
               when (response) {
                  is ResultWrapper.Success -> {
                     userInfo.value = response.value
                  }
                  is ResultWrapper.Error -> {
                     response.exception.printStackTrace()
                  }
               }
            }
         }
      }
   }
}