package kr.onekey.of.network

import kr.onekey.of.base.BaseResponse

sealed class ResultWrapper<out T> {
   data class Success<out T>(val value: T): ResultWrapper<T>()
   data class Error(val exception: Exception) : ResultWrapper<Nothing>()
}

fun ResultWrapper<BaseResponse>.convertBoolResult() : Boolean {
   return when (this) {
      is ResultWrapper.Success -> {
         true
      }
      is ResultWrapper.Error -> {
         false
      }
   }
}