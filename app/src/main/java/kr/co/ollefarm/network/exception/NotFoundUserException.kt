package kr.co.ollefarm.network.exception

class NotFoundUserException : ApiBaseException(NOT_FOUND_USER_EXCEPTION) {
    override val code = NOT_FOUND_USER_EXCEPTION_CODE
}