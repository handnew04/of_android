package kr.onekey.of.di

import kr.onekey.of.repository.LoginRepository
import kr.onekey.of.repository.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
   single { LoginRepository(get(), get(named(DI_PREF_UTIL))) }
   single { UserRepository(get()) }
}