package com.android.sj.domain.usecase.weather

import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<WeatherData>>
}