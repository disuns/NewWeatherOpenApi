package com.project.newweatheropenapi.viewmodel

import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeatherRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeekRainSkyRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.toMap
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeekRainSkyResponse
import com.project.newweatheropenapi.network.repository.WeatherRepository
import com.project.newweatheropenapi.utils.DATA_POTAL_SERVICE_KEY
import com.project.newweatheropenapi.utils.DATA_TYPE_UPPER
import com.project.newweatheropenapi.utils.DataConverter
import com.project.newweatheropenapi.utils.NUM_OF_ROWS_DEFAULT
import com.project.newweatheropenapi.utils.NUM_OF_ROWS_WEEK
import com.project.newweatheropenapi.utils.PAGE_NO_DEFAULT
import com.project.newweatheropenapi.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository,
    private val timeManager: TimeManager,
    private val dataConverter: DataConverter) :
    BaseViewModel() {
    private val _weatherStateFlow = MutableStateFlow<ApiResult<WeatherResponse>>(ApiResult.Empty)
    val weatherStateFlow: StateFlow<ApiResult<WeatherResponse>> = _weatherStateFlow

    private val _timeWeatherState = MutableStateFlow<ApiResult<WeatherResponse>>(ApiResult.Empty)
    val timeWeatherState: StateFlow<ApiResult<WeatherResponse>> get() = _timeWeatherState

    private val _weekRainSkyState = MutableStateFlow<ApiResult<WeekRainSkyResponse>>(ApiResult.Empty)
    val weekRainSkyState: StateFlow<ApiResult<WeekRainSkyResponse>> get() = _weekRainSkyState

    fun fetchAllWeatherData(
        nx: String,
        ny: String,
        address: String){
        val convertLatLng = dataConverter.convertGRIDGPS(0, LatLng(nx.toDouble(),ny.toDouble()))
        val latitude = convertLatLng.latitude.toInt()
        val longitude = convertLatLng.longitude.toInt()
        val landCode = dataConverter.landCodeGu(address)

        viewModelScope.launch {
            val weatherDeferred = async { fetchWeather(latitude.toString(), longitude.toString()) }
            val nowWeatherDeferred = async { fetchNowWeather(latitude.toString(), longitude.toString()) }
            val weekRainSkyDeferred = async { fetchWeekRainSky(landCode) }

            weatherDeferred.await()
            nowWeatherDeferred.await()
            weekRainSkyDeferred.await()
        }
    }

    private fun fetchWeather(
        nx: String,
        ny: String
    ) {
        val request = WeatherRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            timeManager.urlTimeWeatherDate(),
            timeManager.urlTimeWeatherTime(),
            nx,
            ny
        )
        fetchData({ repository.getWeather(request.toMap()) }, _weatherStateFlow)
    }

    private fun fetchNowWeather(
        nx: String,
        ny: String
    ) {
        val request = WeatherRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            timeManager.urlNowDate(),
            timeManager.urlNowTime(),
            nx,
            ny
        )
        fetchData({ repository.getTimeWeather(request.toMap()) }, _timeWeatherState)
    }

    private fun fetchWeekRainSky(
        @Query("regId") regId: String
    ) {
        val request = WeekRainSkyRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_WEEK,
            DATA_TYPE_UPPER,
            regId,
            timeManager.urlWeekWeatherTime()
        )
        fetchData({ repository.getWeekRainSky(request.toMap()) }, _weekRainSkyState)
    }
}