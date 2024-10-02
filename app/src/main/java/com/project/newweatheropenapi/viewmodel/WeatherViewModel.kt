package com.project.newweatheropenapi.viewmodel

import android.content.Context
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.dataclass.state.WeatherViewState
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeatherRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.WeekRainSkyRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.toMap
import com.project.newweatheropenapi.network.repository.WeatherRepository
import com.project.newweatheropenapi.sealed.intent.WeatherIntent
import com.project.newweatheropenapi.utils.DATA_POTAL_SERVICE_KEY
import com.project.newweatheropenapi.utils.DATA_TYPE_UPPER
import com.project.newweatheropenapi.utils.NUM_OF_ROWS_DEFAULT
import com.project.newweatheropenapi.utils.NUM_OF_ROWS_WEEK
import com.project.newweatheropenapi.utils.PAGE_NO_DEFAULT
import com.project.newweatheropenapi.utils.convertGRIDGPS
import com.project.newweatheropenapi.utils.landCodeGu
import com.project.newweatheropenapi.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val timeManager: TimeManager,
    @ApplicationContext val context: Context
) :
    BaseViewModel<WeatherViewState>(WeatherViewState()) {

    fun handleIntent(intent: WeatherIntent) {
        super.handleIntent(intent)
        when (intent) {
            is WeatherIntent.LoadAllWeather -> fetchAllWeatherData(
                intent.nx,
                intent.ny,
                intent.address
            )

            is WeatherIntent.LoadWeather -> fetchWeather(intent.nx, intent.ny)
            is WeatherIntent.LoadTimeWeather -> fetchTimeWeather(intent.nx, intent.ny)
            is WeatherIntent.LoadWeekRainSky -> fetchWeekRainSky(intent.address)
        }
    }

    private fun fetchAllWeatherData(
        nx: String,
        ny: String,
        address: String
    ) {
        fetchAllData(
            { fetchWeather(nx, ny) },
            { fetchTimeWeather(nx, ny) },
            { fetchWeekRainSky(address) }
        )
    }

    private fun fetchWeather(
        nx: String,
        ny: String
    ) {
        val convertLatLng = LatLng(nx.toDouble(), ny.toDouble()).convertGRIDGPS(0)
        val latitude = convertLatLng.latitude.toInt().toString()
        val longitude = convertLatLng.longitude.toInt().toString()
        val request = WeatherRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            timeManager.urlNowDate(),
            timeManager.urlNowTime(),
            latitude,
            longitude
        )
        fetchData({ repository.getWeather(request.toMap()) },
            { currentState, result -> currentState.copy(weatherState = result) })
    }

    private fun fetchTimeWeather(
        nx: String,
        ny: String
    ) {
        val convertLatLng = LatLng(nx.toDouble(), ny.toDouble()).convertGRIDGPS(0)
        val latitude = convertLatLng.latitude.toInt().toString()
        val longitude = convertLatLng.longitude.toInt().toString()
        val request = WeatherRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            timeManager.urlTimeWeatherDate(),
            timeManager.urlTimeWeatherTime(),
            latitude,
            longitude
        )
        fetchData({ repository.getTimeWeather(request.toMap()) },
            { currentState, result -> currentState.copy(timeWeatherState = result) })
    }

    private fun fetchWeekRainSky(
        @Query("regId") regId: String
    ) {
        val landCode = regId.landCodeGu(context = context)
        val request = WeekRainSkyRequest(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_WEEK,
            DATA_TYPE_UPPER,
            landCode,
            timeManager.urlWeekWeatherTime()
        )
        fetchData({ repository.getWeekRainSky(request.toMap()) },
            { currentState, result -> currentState.copy(weekRainSkyState = result) })
    }
}