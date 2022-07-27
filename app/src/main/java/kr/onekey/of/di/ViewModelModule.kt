package kr.onekey.of.di

import kr.onekey.of.ui.login.LoginViewModel
import kr.onekey.of.ui.main.MainViewModel
import kr.onekey.of.ui.set.SettingViewModel
import kr.onekey.of.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { LoginViewModel(get()) }
   viewModel { MainViewModel(get(named(DI_PREF_UTIL))) }
   viewModel { SettingViewModel(get(named(DI_PREF_UTIL)), get()) }
   viewModel { SplashViewModel(get(named(DI_PREF_UTIL)), get()) }
}