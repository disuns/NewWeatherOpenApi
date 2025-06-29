package com.android.sj.domain.mappers

import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseMapper {
    fun <T, R> apiResultMapper(
        response: Flow<ApiResult<T>>,
        handleSuccess: (T) -> ApiResult<R>
    ): Flow<ApiResult<R>> {
        return response.map { flow ->
            when (flow) {
                is ApiResult.Success -> handleSuccess(flow.value)
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
}