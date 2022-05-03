package kr.onekey.of.network.exception

class ApiBaseException(message: String?) : Exception(message) {
   companion object {
      const val BAD_REQUEST_EXCEPTION = "bad request"
      const val INVALID_TOKEN_EXCEPTION = "invalid token"
      const val INVALID_USER = "invalid user"
      const val EMPTY_BODY_EXCEPTION = "empty body"
      const val UNKNOWN_EXCEPTION = "unknown exception"
   }
}