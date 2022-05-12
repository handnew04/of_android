package kr.onekey.of.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.network.exception.NotFoundUserException
import kr.onekey.of.repository.LoginRepository
import kr.onekey.of.util.PrefUtil

class LoginViewModel(private val loginRepository: LoginRepository, private val prefUtil: PrefUtil) : BaseViewModel() {
   val successLogin = MutableLiveData<Boolean>()
   val inputId = MutableLiveData<String>()
   val inputPassword = MutableLiveData<String>()
   val checkedAutoLogin = MutableLiveData<Boolean>()

   fun login() {
      if (isAutoLogin()) {
         prefUtil.apply {
            saveId(inputId.value!!)
            savePassword(inputPassword.value!!)
         }
      }

      launch {
         val response = loginRepository.login(inputId.value!!, inputPassword.value!!)

         withContext(Dispatchers.Main) {
            when (response) {
               is ResultWrapper.Success -> {
                  successLogin.value = true
               }
               is ResultWrapper.Error -> {
                  if (response.exception == NotFoundUserException()) {
                     Log.e(TAG, response.exception.message!!)
                  }
               }
            }
         }
      }
   }

   private fun isAutoLogin() = checkedAutoLogin.value!!
}