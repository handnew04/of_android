package kr.onekey.of

import android.app.Application
import kr.onekey.of.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OFApplication : Application() {
   override fun onCreate() {
      super.onCreate()

      startKoin {
         androidLogger()
         androidContext(this@OFApplication)
         modules(listOf(networkModule))
      }
   }
}