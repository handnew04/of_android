package kr.onekey.of.di

import kr.onekey.of.repository.LoginRepository
import org.koin.dsl.module

val repositoryModule = module {
   single { LoginRepository(get()) }
}