package kr.onekey.of.network

import android.util.Log
import kr.onekey.of.network.exception.ApiBaseException
import kr.onekey.of.network.exception.ApiBaseException.Companion.EMPTY_BODY_EXCEPTION
import kr.onekey.of.network.exception.EmptyBodyException
import kr.onekey.of.network.exception.NetworkBaseException
import kr.onekey.of.network.exception.NetworkBaseException.Companion.HTTP_EXCEPTION
import kr.onekey.of.network.exception.NetworkBaseException.Companion.IO_EXCEPTION
import kr.onekey.of.network.exception.NetworkBaseException.Companion.SOCKET_TIMEOUT_EXCEPTION
import kr.onekey.of.network.exception.NetworkBaseException.Companion.UNKNOWN_EXCEPTION
import kr.onekey.of.network.exception.NotFoundUserException
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ApiHandler {
   suspend inline fun <T> apiCall(
      crossinline apiFunction: suspend () -> Response<T>
   ): ResultWrapper<T> {
      try {
         val response: Response<T> = apiFunction.invoke()

         return if (response.isSuccessful) {
            if (response.body() == null) {
               ResultWrapper.Error(EmptyBodyException())
            } else {
               ResultWrapper.Success<T>(response.body()!!)
            }
         } else {
            val errorBody = response.errorBody()
            Log.e("ApiCall response is not successful", errorBody?.string() ?: "errorBody is null")

            ResultWrapper.Error(mapApiException(response.code()))
         }
      } catch (t: Throwable) {
         Log.e("ApiCalls", "Call error : ${t.localizedMessage}", t.cause)
         return ResultWrapper.Error(mapNetworkThrowable(t))
      }
   }

   private fun getErrorBody(responseBody: ResponseBody?): String {
      return try {
         val jsonObject = JSONObject(responseBody!!.toString())
         when {
            jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
            jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
            else -> "Something wrong happened"
         }
      } catch (e: Exception) {
         "Something wrong happened"
      }
   }

   fun mapApiException(code: Int) : ApiBaseException {
      return when (code) {
         404 -> {
            NotFoundUserException()
         }
         else -> {
            ApiBaseException(UNKNOWN_EXCEPTION)
         }
      }
   }

   fun mapNetworkThrowable(throwable: Throwable): NetworkBaseException {
      return when (throwable) {
         is HttpException -> {
            NetworkBaseException(HTTP_EXCEPTION)
         }
         is SocketTimeoutException -> {
            NetworkBaseException(SOCKET_TIMEOUT_EXCEPTION)
         }
         is IOException -> {
            NetworkBaseException(IO_EXCEPTION)
         }
         else -> {
            NetworkBaseException(UNKNOWN_EXCEPTION)
         }
      }
   }

   companion object {
      private const val MESSAGE_KEY = "message"
      private const val ERROR_KEY = "error"
   }
}