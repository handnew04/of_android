package kr.onekey.of

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import kr.onekey.of.di.networkModule
import kr.onekey.of.di.repositoryModule
import kr.onekey.of.di.utilModule
import kr.onekey.of.di.viewModelModule
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

      getFCMToken()
   }

   private fun getFCMToken() {
      FirebaseMessaging.getInstance().token.addOnCompleteListener { task->
         if (!task.isSuccessful) {
            Log.w("Application", "Fetching FCM registration failed", task.exception)
            return@addOnCompleteListener
         }

         val token = task.result

         Log.e("Token", token)
      }
   }
}