package com.android.sj.domain.repositories

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.TimeWeatherData
import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import kotlinx.coroutines.channels.Channel

interface WeatherRepository {
    fun fetchWeather(date: String, time: String, lat: String, lon: String) : Channel<ApiResult<WeatherData>>
    fun fetchTimeWeather(date: String, time: String, lat: String, lon: String) : Channel<ApiResult<TimeWeatherData>>
    fun fetchWeekRainSky(landCode: String, time: String) : Channel<ApiResult<WeekRainSkyData>>
}