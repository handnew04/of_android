package kr.co.ollefarm

import android.app.Application
import android.content.Intent
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import kr.co.ollefarm.di.networkModule
import kr.co.ollefarm.di.repositoryModule
import kr.co.ollefarm.di.utilModule
import kr.co.ollefarm.di.viewModelModule
import kr.co.ollefarm.ui.login.LoginActivity
import kr.co.ollefarm.ui.login.LoginActivity.Companion.INVALID_TOKEN_LOGIN
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class OFApplication : Application() {
   override fun onCreate() {
      super.onCreate()

      startKoin {
         androidLogger(Level.ERROR)
         androidContext(this@OFApplication)
         modules(listOf(networkModule, repositoryModule, viewModelModule, utilModule))
      }

      FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(isRelease())
   }


   fun showLoginActivity() {
      val intent = Intent(this, LoginActivity::class.java)
      intent.apply {
         flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
         flags = Intent.FLAG_ACTIVITY_NEW_TASK
         putExtra(INVALID_TOKEN_LOGIN, true)
      }
      startActivity(intent)
   }

   private fun isRelease() = !BuildConfig.DEBUG
}