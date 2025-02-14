package com.android.sj.data.network.datasource.impl

import com.android.sj.data.network.datasource.WeatherDataSource
import com.android.sj.data.network.safeChannel
import com.android.sj.data.network.service.WeatherService
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(private val service: WeatherService) :
    WeatherDataSource {
    override fun fetchWeather(params: Map<String, String>) = safeChannel { service.fetchWeather(params) }
    override fun fetchTimeWeather(params: Map<String, String>) = safeChannel { service.fetchTimeWeather(params) }
    override fun fetchWeekRainSky(params: Map<String, String>) = safeChannel { service.fetchWeekRainSky(params) }
}