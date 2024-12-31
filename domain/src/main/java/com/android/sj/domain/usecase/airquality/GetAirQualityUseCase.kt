package com.android.sj.domain.usecase.airquality

import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetAirQualityUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<AirQualityData>>
}