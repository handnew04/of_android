package kr.onekey.of.network.exception

class InvalidTokenException : ApiBaseException(INVALID_TOKEN_EXCEPTION) {
   override val code = INVALID_TOKEN_EXCEPTION_CODE
}