package kr.co.ollefarm.di

import kr.co.ollefarm.util.PrefUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DI_PREF_UTIL = "DI_PREF_UTIL"

val utilModule = module {
   single(named(DI_PREF_UTIL)) { PrefUtil(androidContext()) }
}