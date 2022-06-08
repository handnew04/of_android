package kr.onekey.of.ui.set

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.model.UserInfo
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

   fun uploadPicture(filePath: String) {
      launch {
         val fileByteArray = PictureUtil.convertToByteArray(filePath)
         val pictureInputStream = PictureUtil.getInputStream(fileByteArray)
         val fileToServer = convertToMultiPartBody(pictureInputStream)

         //여차저차 파일 보내기
      }
   }

   private fun convertToMultiPartBody(inputString: InputStream) : MultipartBody.Part {
      val request = inputString.readBytes().toRequestBody("image/*".toMediaType())
      val userId = prefUtil.getId()

      return MultipartBody.Part.createFormData(
         name = "file",
         filename = "${userId}.jpg",
         request
      )
   }
}