package com.android.sj.domain.repositories

import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.TimeWeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(params: Map<String, String>) : Flow<ApiResult<WeatherData>>
    fun fetchTimeWeather(params: Map<String, String>) : Flow<ApiResult<TimeWeatherData>>
    fun fetchWeekRainSky(params: Map<String, String>) : Flow<ApiResult<WeekRainSkyData>>
}