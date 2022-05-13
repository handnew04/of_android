package kr.onekey.of.ui.splash

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.repository.LoginRepository
import kr.onekey.of.util.PrefUtil

class SplashViewModel(
    private val prefUtil: PrefUtil,
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    val succeedLogin = MutableLiveData<Boolean>()

    fun checkAutoLogin() {
        if (isAutoLoginUser()) {
            launch {
                val response = loginRepository.login(prefUtil.getId()!!, prefUtil.getPassword()!!)

                withContext(Dispatchers.Main) {
                    when (response) {
                        is ResultWrapper.Success -> {
                            succeedLogin.value = true
                        }
                        is ResultWrapper.Error -> {
                            succeedLogin.value = false
                        }
                    }
                }
            }
        } else {
            succeedLogin.value = false
        }
    }

    private fun isAutoLoginUser() = prefUtil.getPassword()!!.isNotEmpty()
}