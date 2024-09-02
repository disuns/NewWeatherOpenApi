package com.project.newweatheropenapi.viewmodel

import com.project.newweatheropenapi.common.DATA_POTAL_SERVICE_KEY
import com.project.newweatheropenapi.common.DATA_TYPE_UPPER
import com.project.newweatheropenapi.common.NUM_OF_ROWS_DEFAULT
import com.project.newweatheropenapi.common.NUM_OF_ROWS_WEEK
import com.project.newweatheropenapi.common.PAGE_NO_DEFAULT
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeatherRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeekRainSkyRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.toMap
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeekRainSkyResponse
import com.project.newweatheropenapi.network.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    BaseViewModel() {
    private val _weatherStateFlow = MutableStateFlow<ApiResult<WeatherResponse>>(ApiResult.Loading)
    val weatherStateFlow: StateFlow<ApiResult<WeatherResponse>> = _weatherStateFlow

    private val _nowWeatherState = MutableStateFlow<ApiResult<WeatherResponse>>(ApiResult.Loading)
    val nowWeatherState: StateFlow<ApiResult<WeatherResponse>> get() = _nowWeatherState

    private val _weekRainSkyState = MutableStateFlow<ApiResult<WeekRainSkyResponse>>(ApiResult.Loading)
    val weekRainSkyState: StateFlow<ApiResult<WeekRainSkyResponse>> get() = _weekRainSkyState

    fun fetchWeather(
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ) {
        val request = WeatherRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            base_date,
            base_time,
            nx,
            ny
        )
        fetchData({ repository.getWeather(request.toMap()) }, _weatherStateFlow)
    }

    fun fetchNowWeather(
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ) {
        val request = WeatherRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            base_date,
            base_time,
            nx,
            ny
        )
        fetchData({ repository.getNowWeather(request.toMap()) }, _nowWeatherState)
    }

    fun fetchWeekRainSky(
        @Query("regId") regId: String,
        @Query("tmFc") tmFc: String
    ) {
        val request = WeekRainSkyRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_WEEK,
            DATA_TYPE_UPPER,
            regId,
            tmFc
        )
        fetchData({ repository.getWeekRainSky(request.toMap()) }, _weekRainSkyState)
    }
}