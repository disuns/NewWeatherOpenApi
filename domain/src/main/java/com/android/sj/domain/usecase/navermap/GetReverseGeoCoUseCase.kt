package com.android.sj.domain.usecase.navermap

import com.android.sj.domain.models.NaverMapData
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetReverseGeoCoUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<NaverMapData>>
}