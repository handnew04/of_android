package kr.onekey.of.network

import java.lang.Exception

sealed class ResultWrapper<out T> {
   data class Success<out T>(val value: T): ResultWrapper<T>()
   data class Error(val exception: Exception) : ResultWrapper<Nothing>()
}