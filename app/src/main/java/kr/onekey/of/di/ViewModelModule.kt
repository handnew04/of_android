package kr.onekey.of.di

import kr.onekey.of.ui.login.LoginViewModel
import kr.onekey.of.ui.main.MainViewModel
import kr.onekey.of.ui.set.SettingViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
   viewModel { LoginViewModel(get()) }
   viewModel { MainViewModel() }
   viewModel { SettingViewModel() }
}