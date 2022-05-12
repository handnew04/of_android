package kr.onekey.of.network.exception

class NotFoundUserException : ApiBaseException(NOT_FOUND_USER_EXCEPTION) {
    override val code = 404
}