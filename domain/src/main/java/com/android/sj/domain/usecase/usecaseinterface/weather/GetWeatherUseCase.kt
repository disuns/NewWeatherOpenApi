package com.android.sj.domain.usecase.usecaseinterface.weather

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.WeatherData
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    operator fun invoke(date: String, time: String, lat: String, lon: String) : Flow<ApiResult<WeatherData>>
}