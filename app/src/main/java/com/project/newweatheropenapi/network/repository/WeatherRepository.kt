package com.project.newweatheropenapi.network.repository

import com.project.newweatheropenapi.DATA_POTAL_SERVICE_KEY
import com.project.newweatheropenapi.DATA_TYPE_UPPER
import com.project.newweatheropenapi.NUM_OF_ROWS_DEFAULT
import com.project.newweatheropenapi.NUM_OF_ROWS_WEEK
import com.project.newweatheropenapi.PAGE_NO_DEFAULT
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeatherRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeekRainSkyRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.toMap
import com.project.newweatheropenapi.network.safeFlow
import com.project.newweatheropenapi.network.service.WeatherService
import retrofit2.http.Query
import retrofit2.http.QueryMap
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: WeatherService) {
    fun getWeather(@QueryMap params: Map<String, String>) = safeFlow { service.getWeather(params) }

    fun getNowWeather(@QueryMap params: Map<String, String>) = safeFlow { service.getNowWeather(params) }

    fun getWeekRainSky(@QueryMap params: Map<String, String>) = safeFlow { service.getWeekRainSky(params) }
}