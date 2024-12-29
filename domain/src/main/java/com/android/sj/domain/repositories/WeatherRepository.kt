package com.android.sj.domain.repositories

import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(params: Map<String, String>) : Flow<ApiResult<WeatherData>>
    fun fetchTimeWeather(params: Map<String, String>) : Flow<ApiResult<WeatherData>>
    fun fetchWeekRainSky(params: Map<String, String>) : Flow<ApiResult<WeekRainSkyData>>
}