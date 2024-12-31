package com.android.sj.domain.usecase.weather

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.TimeWeatherData
import kotlinx.coroutines.flow.Flow

interface GetTimeWeatherUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<TimeWeatherData>>
}