package com.android.sj.domain.mappers

import com.android.sj.domain.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

abstract class BaseMapper {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T, R> apiResultMapper(
        response: ReceiveChannel<ApiResult<T>>,
        handleSuccess: (T) -> ApiResult<R>
    ): ReceiveChannel<ApiResult<R>> {
        return CoroutineScope(Dispatchers.IO).produce {
            for (result in response) {
                val mappedResult = when (result) {
                    is ApiResult.Success -> handleSuccess(result.value)
                    is ApiResult.Empty -> ApiResult.Empty
                    is ApiResult.Loading -> ApiResult.Loading
                    is ApiResult.Error -> ApiResult.Error(result.code, result.exception)
                }
                send(mappedResult)
            }
        }
    }
}