package kr.co.ollefarm.network.exception

class InvalidTokenException : ApiBaseException(INVALID_TOKEN_EXCEPTION) {
   override val code = INVALID_TOKEN_EXCEPTION_CODE
}