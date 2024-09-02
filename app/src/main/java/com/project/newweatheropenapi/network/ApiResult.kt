package com.project.newweatheropenapi.network

import com.project.newweatheropenapi.common.logMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    object Loading : ApiResult<Nothing>()
    object Empty: ApiResult<Nothing>()
    data class Error(val code : Int? = null, val exception : Throwable? = null): ApiResult<Nothing>()
}

fun <T> safeFlow(apiFunc: suspend () -> Response<T>): Flow<ApiResult<T>> = flow{
    emit(ApiResult.Loading)
    try {
        val response = apiFunc()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiResult.Success(it))
            } ?: emit(ApiResult.Empty)
        } else {
            logMessage("API Error: ${response.code()} - ${response.message()}")
            emit(ApiResult.Error(code = response.code(), exception = HttpException(response)))
        }
    } catch (e: IOException) {
        logMessage("Network Error : $e")
        emit(ApiResult.Error(exception = e))
    } catch (e: Exception) {
        logMessage("Unexpected Error : $e")
        emit(ApiResult.Error(exception = e))
    }
}