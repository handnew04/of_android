package kr.co.ollefarm.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.ollefarm.BuildConfig
import kr.co.ollefarm.R
import kr.co.ollefarm.base.BaseViewModel
import kr.co.ollefarm.repository.LoginRepository
import kr.co.ollefarm.util.PrefUtil

class SplashViewModel(
   private val prefUtil: PrefUtil,
   private val loginRepository: LoginRepository
) : BaseViewModel() {
   val succeedLogin = MutableLiveData<Boolean>()
   val isSupportVersion = MutableLiveData<Boolean>()

   fun checkAutoLogin() {
      if (isAutoLoginUser()) {
         launch {
            val isSuccessLogin =
               loginRepository.login(prefUtil.getEmail()!!, prefUtil.getPassword()!!)

            withContext(Dispatchers.Main) {
               succeedLogin.value = isSuccessLogin
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