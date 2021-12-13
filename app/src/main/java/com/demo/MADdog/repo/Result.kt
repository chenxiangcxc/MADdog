package com.demo.MADdog.repo

import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
}

fun buildException(e: Throwable): Exception {
    return if (e is HttpException) {
        Exception("Http Exception(${e.code()})")
    } else if (e is UnknownHostException) {
        Exception("Unknown Host")
    } else if (e is SocketTimeoutException) {
        Exception("Request Timeout")
    } else if (e is IOException) {
        Exception("Network IO exception(${e.message})")
    } else if (e is JsonParseException || e is JSONException) {
        Exception("Json parse error")
    } else {
        Exception("Server Error(${e.message})")
    }
}
