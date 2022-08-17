package kr.onekey.of.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.onekey.of.BuildConfig
import kr.onekey.of.R
import kr.onekey.of.base.BaseViewModel
import kr.onekey.of.network.ResultWrapper
import kr.onekey.of.repository.LoginRepository
import kr.onekey.of.util.PrefUtil

class SplashViewModel(
   private val prefUtil: PrefUtil,
   private val loginRepository: LoginRepository
) : BaseViewModel() {
   val succeedLogin = MutableLiveData<Boolean>()
   val isSupportVersion = MutableLiveData<Boolean>()

   fun checkAutoLogin() {
      if (isAutoLoginUser()) {
         launch {
            val response = loginRepository.login(prefUtil.getEmail()!!, prefUtil.getPassword()!!)

            withContext(Dispatchers.Main) {
               when (response) {
                  is ResultWrapper.Success -> {
                     succeedLogin.value = true
                     loginRepository.apply {
                        saveId(response.value.userId)
                        saveTokens(response.value.accessToken, response.value.refreshToken)
                     }
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

   fun checkEssentialUpdate() {
      val appVersion = BuildConfig.VERSION_NAME
      val remoteConfig = FirebaseRemoteConfig.getInstance()
      val settings = FirebaseRemoteConfigSettings.Builder()
         .setMinimumFetchIntervalInSeconds(0)
         .build()

      remoteConfig.setConfigSettingsAsync(settings)
      remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
      remoteConfig.fetchAndActivate().addOnCompleteListener {
         val remoteVersion = FirebaseRemoteConfig.getInstance()
         val minimumVersion = remoteVersion.getString("android_version")

         val appVersionArray = appVersion.split(".")
         val remoteVersionArray = minimumVersion.split(".")

         if (minimumVersion.isBlank()) {
            isSupportVersion.value = true
            return@addOnCompleteListener
         }

         for (i in remoteVersionArray.indices) {
            if (appVersionArray[i].toInt() < remoteVersionArray[i].toInt()) {
               Log.w(
                  TAG,
                  "app : ${appVersionArray[i]} , remote : ${remoteVersionArray[i]}"
               )
               isSupportVersion.value = false
               return@addOnCompleteListener
            } else if (appVersionArray[i].toInt() > remoteVersionArray[i].toInt()) {
               Log.w(
                  TAG,
                  "app : ${appVersionArray[i]} , remote : ${remoteVersionArray[i]}"
               )
               isSupportVersion.value = true
               return@addOnCompleteListener
            }
         }

         //FirebaseCrashlytics.getInstance().log("is SupportVersion, appVersion == remoteVersion")
         isSupportVersion.value = true
      }
   }


   private fun isAutoLoginUser() = prefUtil.getPassword()!!.isNotEmpty()
}