package kr.onekey.of.ui.set

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.model.UserInfo
import kr.onekey.of.model.UserItem
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.repository.UserRepository
import kr.onekey.of.util.PictureUtil
import kr.onekey.of.util.PrefUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class SettingViewModel(private val prefUtil: PrefUtil, private val userRepository: UserRepository) :
   BaseViewModel() {
   val userInfo = MutableLiveData<UserItem>()
   var userName = MutableLiveData<String>()
   var userPhoneNumber = MutableLiveData<String>()
   val isCompletedUpdatingUserInfo = MutableLiveData<Boolean>()
   private val userId: Int by lazy { prefUtil.getId() }

   fun getUser() {
      launch {
         val response = userRepository.getUserInfo(userId)

         withContext(Dispatchers.Main) {
            when (response) {
               is ResultWrapper.Success -> {
                  userInfo.value = response.value
                  userName.value = response.value.name
                  userPhoneNumber.value = response.value.phoneNumber
               }
               is ResultWrapper.Error -> {
                  response.exception.printStackTrace()
               }
            }
         }
      }
   }

   fun updateUser() {
      if (userName.value.isNullOrBlank()  || userPhoneNumber.value.isNullOrBlank()) {
         return
      }

      val userInfo = UserInfo(name = userName.value!!, phoneNumber = userPhoneNumber.value!!)
      launch {
         val response = userRepository.updateUser(userId, userInfo)

         withContext(Dispatchers.Main) {
            when (response) {
               is ResultWrapper.Success -> {
                  isCompletedUpdatingUserInfo.value = true
               }
               is ResultWrapper.Error -> {
                  isCompletedUpdatingUserInfo.value = false
                  response.exception.printStackTrace()
               }
            }
         }
      }

   }

   fun uploadPicture(filePath: String) {
      launch {
         val fileByteArray = PictureUtil.convertToByteArray(filePath)
         val pictureInputStream = PictureUtil.getInputStream(fileByteArray)
         val fileToServer = convertToMultiPartBody(pictureInputStream)

         //여차저차 파일 보내기
      }
   }

   private fun convertToMultiPartBody(inputString: InputStream): MultipartBody.Part {
      val request = inputString.readBytes().toRequestBody("image/*".toMediaType())
      val userId = prefUtil.getId()

      return MultipartBody.Part.createFormData(
         name = "file",
         filename = "${userId}.jpg",
         request
      )
   }
}