package kr.onekey.of.network.exception

import kotlin.properties.Delegates

open class ApiBaseException(message: String?) : Exception(message) {
   open val code by Delegates.notNull<Int>()

   companion object {
      const val BAD_REQUEST_EXCEPTION = "bad request"
      const val INVALID_TOKEN_EXCEPTION = "invalid token"
      const val INVALID_USER_EXCEPTION = "invalid user"
      const val NOT_FOUND_USER_EXCEPTION = "not found user"
      const val EMPTY_BODY_EXCEPTION = "empty body"
      const val UNKNOWN_EXCEPTION = "unknown exception"

      const val INVALID_TOKEN_EXCEPTION_CODE = 401
      const val NOT_FOUND_USER_EXCEPTION_CODE = 404
   }
}