package com.android.sj.data.network.datasource

import com.android.sj.data.network.response.datapotal.WeatherResponse
import com.android.sj.data.network.response.datapotal.WeekRainSkyResponse
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.QueryMap

interface WeatherDataSource {
    fun fetchWeather(@QueryMap params: Map<String, String>) : Flow<ApiResult<WeatherResponse>>
    fun fetchTimeWeather(@QueryMap params: Map<String, String>) : Flow<ApiResult<WeatherResponse>>
    fun fetchWeekRainSky(@QueryMap params: Map<String, String>) : Flow<ApiResult<WeekRainSkyResponse>>
}