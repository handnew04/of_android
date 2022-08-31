package kr.co.ollefarm.di

import kr.co.ollefarm.ui.login.LoginViewModel
import kr.co.ollefarm.ui.main.MainViewModel
import kr.co.ollefarm.ui.set.SettingViewModel
import kr.co.ollefarm.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { LoginViewModel(get()) }
   viewModel { MainViewModel(get(named(DI_PREF_UTIL))) }
   viewModel { SettingViewModel(get(named(DI_PREF_UTIL)), get()) }
   viewModel { SplashViewModel(get(named(DI_PREF_UTIL)), get()) }
}