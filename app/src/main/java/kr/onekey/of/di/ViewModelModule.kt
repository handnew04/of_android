package kr.onekey.of.di

import kr.onekey.of.ui.login.LoginViewModel
import kr.onekey.of.ui.main.MainViewModel
import kr.onekey.of.ui.set.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { LoginViewModel(get(), get(named(DI_PREF_UTIL))) }
   viewModel { MainViewModel() }
   viewModel { SettingViewModel(get()) }
}