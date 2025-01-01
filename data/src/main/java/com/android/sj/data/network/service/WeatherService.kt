package com.android.sj.data.network.service

import com.android.sj.data.network.ApiConstants.TIME_WEATHER
import com.android.sj.data.network.ApiConstants.WEATHER
import com.android.sj.data.network.ApiConstants.WEEK_RAIN_SKY
import com.android.sj.data.network.response.datapotal.WeatherResponse
import com.android.sj.data.network.response.datapotal.WeekRainSkyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {
    @GET(WEATHER)
    suspend fun fetchWeather(@QueryMap params : Map<String,String>): Response<WeatherResponse>

    @GET(TIME_WEATHER)
    suspend fun fetchTimeWeather(@QueryMap params : Map<String,String>): Response<WeatherResponse>

    @GET(WEEK_RAIN_SKY)
    suspend fun fetchWeekRainSky(@QueryMap params : Map<String,String>): Response<WeekRainSkyResponse>
}