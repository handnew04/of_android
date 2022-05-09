package kr.onekey.of.di

import kr.onekey.of.repository.LoginRepository
import kr.onekey.of.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
   single { LoginRepository(get()) }
   single { UserRepository(get()) }
}