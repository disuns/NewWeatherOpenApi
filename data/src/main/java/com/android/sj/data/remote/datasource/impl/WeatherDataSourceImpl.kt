package com.android.sj.data.remote.datasource.impl

import com.android.sj.data.remote.datasource.WeatherDataSource
import com.android.sj.data.remote.safeFlow
import com.android.sj.data.remote.service.WeatherService
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(private val service: WeatherService) :
    WeatherDataSource {
    override fun fetchWeather(params: Map<String, String>) = safeFlow { service.fetchWeather(params) }
    override fun fetchTimeWeather(params: Map<String, String>) = safeFlow { service.fetchTimeWeather(params) }
    override fun fetchWeekRainSky(params: Map<String, String>) = safeFlow { service.fetchWeekRainSky(params) }
}