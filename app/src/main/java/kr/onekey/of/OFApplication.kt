package kr.onekey.of

import android.app.Application
import kr.onekey.of.di.networkModule
import kr.onekey.of.di.repositoryModule
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
         modules(listOf(networkModule, repositoryModule, viewModelModule))
      }
   }
}