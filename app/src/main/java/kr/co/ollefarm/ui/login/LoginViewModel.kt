package kr.co.ollefarm.ui.login

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.ollefarm.base.BaseViewModel
import kr.co.ollefarm.repository.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) :
   BaseViewModel() {
   val succeedLogin = MutableLiveData<Boolean>()
   val isWarning = MutableLiveData<Boolean>()
   var inputId = DEFAULT_VALUE
   var inputPassword = DEFAULT_VALUE
   var checkedAutoLogin = false

   fun login() {
      if (inputId.isBlank() || inputPassword.isBlank()) {
         isWarning.value = true
         return
      }

      isWarning.value = false

      if (checkedAutoLogin) {
         loginRepository.savePassword(inputPassword)
      } else {
         loginRepository.initLogin()
      }

      loginRepository.saveEmail(inputId)


      launch {
         val isSuccessLogin = loginRepository.login(inputId, inputPassword)

         withContext(Dispatchers.Main) {
            succeedLogin.value = isSuccessLogin
         }
      }
   }

   companion object {
      const val DEFAULT_VALUE = ""
   }
}