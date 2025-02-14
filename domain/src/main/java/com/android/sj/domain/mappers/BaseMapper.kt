package com.android.sj.domain.mappers

import com.android.sj.domain.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.math.log

abstract class BaseMapper(protected val coroutineScope: CoroutineScope) {
    fun <T, R> apiResultMapper(
        response: Channel<ApiResult<T>>,
        handleSuccess: (T) -> ApiResult<R>
    ): Channel<ApiResult<R>> {
        val channel = Channel<ApiResult<R>>(Channel.BUFFERED)
        coroutineScope.launch {
            for (result in response) {
                val mappedResult = when (result) {
                    is ApiResult.Success -> handleSuccess(result.value)
                    is ApiResult.Empty -> ApiResult.Empty
                    is ApiResult.Loading -> ApiResult.Loading
                    is ApiResult.Error -> ApiResult.Error(result.code, result.exception)
                }
                channel.send(mappedResult)
            }
            channel.close()
        }
        return channel
    }
}