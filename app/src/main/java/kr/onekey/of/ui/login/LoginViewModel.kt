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
            val response = loginRepository.login(inputId, inputPassword)

            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultWrapper.Success -> {
                        succeedLogin.value = true
                        loginRepository.saveId(response.value.id)
                    }
                    is ResultWrapper.Error -> {
                        if (response.exception == NotFoundUserException()) {
                            Log.e(TAG, response.exception.message!!)
                        }

                        succeedLogin.value = false
                    }
                }
            }
        }
    }

    companion object {
        const val DEFAULT_VALUE = ""
    }
}