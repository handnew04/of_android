package kr.co.ollefarm.network

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kr.co.ollefarm.network.exception.*
import kr.co.ollefarm.network.exception.ApiBaseException.Companion.INVALID_TOKEN_EXCEPTION_CODE
import kr.co.ollefarm.network.exception.ApiBaseException.Companion.NOT_FOUND_USER_EXCEPTION_CODE
import kr.co.ollefarm.network.exception.NetworkBaseException.Companion.CONNECT_EXCEPTION
import kr.co.ollefarm.network.exception.NetworkBaseException.Companion.HTTP_EXCEPTION
import kr.co.ollefarm.network.exception.NetworkBaseException.Companion.IO_EXCEPTION
import kr.co.ollefarm.network.exception.NetworkBaseException.Companion.SOCKET_TIMEOUT_EXCEPTION
import kr.co.ollefarm.network.exception.NetworkBaseException.Companion.UNKNOWN_EXCEPTION
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ApiHandler {
   suspend inline fun <T> apiCall(
      crossinline apiFunction: suspend () -> Response<T>
   ): ResultWrapper<T> {
      try {
         val response: Response<T> = apiFunction.invoke()

         return if (response.isSuccessful) {
            if (response.body() == null) {
               FirebaseCrashlytics.getInstance().log("ApiCall: response body is empty")
               ResultWrapper.Error(EmptyBodyException())
            } else {
               FirebaseCrashlytics.getInstance().log("ApiCall: response body : ${response.body()}")
               ResultWrapper.Success<T>(response.body()!!)
            }
         } else {
            val errorBody = response.errorBody()
            FirebaseCrashlytics.getInstance()
               .log("ApiCall response is not successful ${errorBody?.string()} ?: errorBody is null")

            ResultWrapper.Error(mapApiException(response.code()))
         }
      } catch (t: Throwable) {
         FirebaseCrashlytics.getInstance().log("ApiCall Error : ${t.localizedMessage}")
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

   fun mapApiException(code: Int): ApiBaseException {
      return when (code) {
         NOT_FOUND_USER_EXCEPTION_CODE -> {
            NotFoundUserException()
         }
         INVALID_TOKEN_EXCEPTION_CODE -> {
            InvalidTokenException()
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
         is ConnectException -> {
            NetworkBaseException(CONNECT_EXCEPTION)
         }
         else -> {
            NetworkBaseException(UNKNOWN_EXCEPTION)
         }
      }
   }

   companion object {
      private const val MESSAGE_KEY = "message"
      private const val ERROR_KEY = "error"
      const val SUCCESS_CODE = 200
   }
}