package com.project.newweatheropenapi.network.repository

import com.project.newweatheropenapi.network.safeFlow
import com.project.newweatheropenapi.network.service.WeatherService
import retrofit2.http.QueryMap
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: WeatherService) {
    fun getWeather(@QueryMap params: Map<String, String>) = safeFlow { service.getWeather(params) }

    fun getTimeWeather(@QueryMap params: Map<String, String>) = safeFlow { service.getTimeWeather(params) }

    fun getWeekRainSky(@QueryMap params: Map<String, String>) = safeFlow { service.getWeekRainSky(params) }
}