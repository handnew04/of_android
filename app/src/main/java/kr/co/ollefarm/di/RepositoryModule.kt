package kr.co.ollefarm.di

import kr.co.ollefarm.repository.AuthRepository
import kr.co.ollefarm.repository.LoginRepository
import kr.co.ollefarm.repository.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
   single { LoginRepository(get(), get(), get(named(DI_PREF_UTIL))) }
   single { UserRepository(get()) }
   single { AuthRepository(get(), get(named(DI_PREF_UTIL))) }
}