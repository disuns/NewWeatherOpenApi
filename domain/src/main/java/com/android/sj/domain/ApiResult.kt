package com.test.domain

import java.io.IOException

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    object Loading : ApiResult<Nothing>()
    object Empty: ApiResult<Nothing>()
    data class Error(val code : Int? = null, val exception : Throwable? = null): ApiResult<Nothing>()
}