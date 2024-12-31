package com.android.sj.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.usecase.weather.GetTimeWeatherUseCase
import com.android.sj.domain.usecase.weather.GetWeatherUseCase
import com.android.sj.domain.usecase.weather.GetWeekRainSkyUseCase
import com.android.sj.presentation.RequestConstants.DATA_POTAL_SERVICE_KEY
import com.android.sj.presentation.RequestConstants.DATA_TYPE_UPPER
import com.android.sj.presentation.RequestConstants.NUM_OF_ROWS_DEFAULT
import com.android.sj.presentation.RequestConstants.NUM_OF_ROWS_WEEK
import com.android.sj.presentation.RequestConstants.PAGE_NO_DEFAULT
import com.android.sj.presentation.intent.WeatherIntent
import com.android.sj.presentation.managers.TimeManager
import com.android.sj.presentation.mappers.PresentationMapper
import com.android.sj.presentation.models.request.datapotal.WeatherRequest
import com.android.sj.presentation.models.request.datapotal.WeekRainSkyRequest
import com.android.sj.presentation.models.request.datapotal.toMap
import com.android.sj.presentation.state.WeatherViewState
import com.android.sj.presentation.utils.convertGRIDGPS
import com.android.sj.presentation.utils.landCodeGu
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getTimeWeatherUseCase: GetTimeWeatherUseCase,
    private val getWeekRainSkyUseCase: GetWeekRainSkyUseCase,
    private val mapper: PresentationMapper,
    private val timeManager: TimeManager,
    @ApplicationContext val context: Context
) : BaseViewModel<WeatherViewState>(WeatherViewState()) {

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

        viewModelScope.launch {
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

            mapper.domainToUIWeather(getWeatherUseCase(request.toMap())).collect { result ->
                _state.value = _state.value.copy(weatherState = result)
            }
        }
    }

    private fun fetchTimeWeather(
        nx: String,
        ny: String
    ) {
        val convertLatLng = LatLng(nx.toDouble(), ny.toDouble()).convertGRIDGPS(0)
        val latitude = convertLatLng.latitude.toInt().toString()
        val longitude = convertLatLng.longitude.toInt().toString()
        viewModelScope.launch {
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

            mapper.domainToUITimeWeather(getTimeWeatherUseCase(request.toMap())).collect { result ->
                _state.value = _state.value.copy(timeWeatherState = result)
            }
        }
    }

    private fun fetchWeekRainSky(regId: String) {
        val landCode = regId.landCodeGu(context = context)

        viewModelScope.launch {
            val request = WeekRainSkyRequest(
                DATA_POTAL_SERVICE_KEY,
                PAGE_NO_DEFAULT,
                NUM_OF_ROWS_WEEK,
                DATA_TYPE_UPPER,
                landCode,
                timeManager.urlWeekWeatherTime()
            )

            mapper.domainToUIWeekRainSky(getWeekRainSkyUseCase(request.toMap())).collect { result ->
                _state.value = _state.value.copy(weekRainSkyState = result)
            }
        }
    }
}