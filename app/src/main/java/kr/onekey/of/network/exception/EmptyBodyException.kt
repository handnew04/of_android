package kr.onekey.of.network.exception

class EmptyBodyException : ApiBaseException(EMPTY_BODY_EXCEPTION) {
    override val code = 0
}