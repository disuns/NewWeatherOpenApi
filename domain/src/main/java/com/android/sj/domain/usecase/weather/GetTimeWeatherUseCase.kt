package com.android.sj.domain.usecase.weather

import com.android.sj.domain.models.WeatherData
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetTimeWeatherUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<WeatherData>>
}