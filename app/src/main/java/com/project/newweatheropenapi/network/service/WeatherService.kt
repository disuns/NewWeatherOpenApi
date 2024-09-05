package com.project.newweatheropenapi.network.service

import com.project.newweatheropenapi.utils.NOW_FCST
import com.project.newweatheropenapi.utils.VILAGE_FCST
import com.project.newweatheropenapi.utils.WEEK_RAIN_SKY
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeekRainSkyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {
    @GET(VILAGE_FCST)
    suspend fun getWeather(@QueryMap params : Map<String,String>): Response<WeatherResponse>

    @GET(NOW_FCST)
    suspend fun getNowWeather(@QueryMap params : Map<String,String>): Response<WeatherResponse>

    @GET(WEEK_RAIN_SKY)
    suspend fun getWeekRainSky(@QueryMap params : Map<String,String>): Response<WeekRainSkyResponse>
}