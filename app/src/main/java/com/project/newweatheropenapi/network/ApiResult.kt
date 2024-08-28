package com.project.newweatheropenapi.network

import kotlinx.coroutines.flow.flow

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    object Loading : ApiResult<Nothing>()
    object Empty: ApiResult<Nothing>()
    data class Error(val code : Int? = null, val exception : Throwable? = null): ApiResult<Nothing>()
}

fun <T> safeFlow(apiFunc: suspend () -> T) = flow{
    emit(ApiResult.Loading)
    try {
        emit(ApiResult.Success(apiFunc.invoke()))
    } catch (e: NullPointerException) {
        emit(ApiResult.Empty)
    } catch (e: Exception) {
        emit(ApiResult.Error(exception = e))
    }
}