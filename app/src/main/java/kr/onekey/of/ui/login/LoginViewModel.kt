package kr.onekey.of.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.repository.LoginRepository
import kr.onekey.of.util.PrefUtil

class LoginViewModel(private val loginRepository: LoginRepository, private val prefUtil: PrefUtil) : BaseViewModel() {
   val successLogin = MutableLiveData<Boolean>()
   val inputId = MutableLiveData<String>()
   val inputPassword = MutableLiveData<String>()
   val checkedAutoLogin = MutableLiveData<Boolean>()

   fun testtest() {
      launch {
         val response = loginRepository.test()

         withContext(Dispatchers.Main) {
            when (response) {
               is ResultWrapper.Success -> {
                  Log.e(TAG, response.value.string())
               }
               is ResultWrapper.Error -> {
                  Log.e(TAG, response.exception.message.toString())
               }
            }
         }
      }
   }

   fun login() {
      if (isAutoLogin()) {
         prefUtil.apply {
            saveId(inputId.value!!)
            savePassword(inputPassword.value!!)
         }
      }

      // TODO: 2022/05/09 login api생기면 수정할 것
      //val response = loginRepository.login(inputId, inputPassword)

//      withContext(Dispatchers.Main) {
//         when (response) {
//            is ResultWrapper.Success -> {
//
//            }
//            is ResultWrapper.Error -> {
//               if (response.exception)
//            }
//         }
//      }
   }

   private fun isAutoLogin() = checkedAutoLogin.value!!
}