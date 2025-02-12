package com.android.sj.data.network

import com.android.sj.common.utils.logMessage
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> safeChannel(apiFunc: suspend () -> Response<T>): ReceiveChannel<ApiResult<T>> =
    CoroutineScope(Dispatchers.IO).produce{
        send(ApiResult.Loading)
        try {
            val response = apiFunc()
            if (response.isSuccessful) {
                response.body()?.let {
                    send(ApiResult.Success(it))
                } ?: send(ApiResult.Empty)
            } else {
                logMessage("API Error: ${response.code()} - ${response.message()}")
                send(ApiResult.Error(code = response.code(), exception = HttpException(response)))
            }
        } catch (e: IOException) {
            logMessage("Network Error : $e")
            send(ApiResult.Error(exception = e))
        } catch (e: Exception) {
            logMessage("Unexpected Error : $e")
            send(ApiResult.Error(exception = e))
        }
}