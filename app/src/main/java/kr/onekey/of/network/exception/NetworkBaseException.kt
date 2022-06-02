package kr.onekey.of.network.exception

import java.lang.Exception

open class NetworkBaseException(message: String?) : Exception(message) {
   companion object {
      const val HTTP_EXCEPTION  = "http exception"
      const val SOCKET_TIMEOUT_EXCEPTION = "socket timeout exception"
      const val IO_EXCEPTION = "io exception"
      const val CONNECT_EXCEPTION = "server connect failed"
      const val UNKNOWN_EXCEPTION = "unknown exception"
   }
}