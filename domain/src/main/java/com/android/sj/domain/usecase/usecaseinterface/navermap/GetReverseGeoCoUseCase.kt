package com.android.sj.domain.usecase.usecaseinterface.navermap

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.NaverMapData
import kotlinx.coroutines.flow.Flow

interface GetReverseGeoCoUseCase {
    operator fun invoke(latLng: String) : Flow<ApiResult<NaverMapData>>
}