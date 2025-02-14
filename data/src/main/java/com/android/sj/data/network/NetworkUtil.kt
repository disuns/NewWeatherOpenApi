package com.android.sj.data.network

import com.android.sj.common.utils.logMessage
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> safeChannel(apiFunc: suspend () -> Response<T>): Channel<ApiResult<T>> {
    val channel = Channel<ApiResult<T>>(Channel.BUFFERED)

    CoroutineScope(Dispatchers.IO).launch {
        channel.send(ApiResult.Loading)
        try {
            val response = apiFunc()
            if (response.isSuccessful) {
                response.body()?.let {
                    channel.send(ApiResult.Success(it))
                } ?: channel.send(ApiResult.Empty)
            } else {
                logMessage("API Error: ${response.code()} - ${response.message()}")
                channel.send(ApiResult.Error(code = response.code(), exception = HttpException(response)))
            }
        } catch (e: IOException) {
            logMessage("Network Error : $e")
            channel.send(ApiResult.Error(exception = e))
        } catch (e: Exception) {
            logMessage("Unexpected Error : $e")
            channel.send(ApiResult.Error(exception = e))
        } finally {
            channel.close()
        }
    }
    return channel
}