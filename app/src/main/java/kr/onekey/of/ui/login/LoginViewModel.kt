package kr.onekey.of.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.repository.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
   val successLogin = MutableLiveData<Boolean>()
   val inputId = MutableLiveData<String>()
   val inputPassword = MutableLiveData<String>()
   val isAutoLogin = MutableLiveData<Boolean>()

   fun testtest() {
      launch {
         val response = loginRepository.test()

         withContext(Dispatchers.Main) {
            when (response) {
               is ResultWrapper.Success -> {
                  Log.e(TAG, response.value.string())
               }
               is ResultWrapper.Error -> {
                  Log.e(TAG, response.toString())
               }
            }
         }
      }
   }
}