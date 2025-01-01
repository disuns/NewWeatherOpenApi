package com.android.sj.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.usecase.usecaseinterface.weather.GetTimeWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeekRainSkyUseCase
import com.android.sj.presentation.intent.WeatherIntent
import com.android.sj.presentation.mappers.WeatherPresentationMapper
import com.android.sj.presentation.models.state.WeatherViewState
import com.android.sj.presentation.utils.convertGRIDGPS
import com.android.sj.presentation.utils.landCodeGu
import com.android.sj.presentation.utils.managers.TimeManager
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
    private val mapper: WeatherPresentationMapper,
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
        val lat = convertLatLng.latitude.toInt().toString()
        val lon = convertLatLng.longitude.toInt().toString()

        viewModelScope.launch {
            mapper.domainToUIWeather(getWeatherUseCase(timeManager.urlNowDate(), timeManager.urlNowTime(), lat, lon)).collect { result ->
                _state.value = _state.value.copy(weatherState = result)
            }
        }
    }

    private fun fetchTimeWeather(
        nx: String,
        ny: String
    ) {
        val convertLatLng = LatLng(nx.toDouble(), ny.toDouble()).convertGRIDGPS(0)
        val lat = convertLatLng.latitude.toInt().toString()
        val lon = convertLatLng.longitude.toInt().toString()
        viewModelScope.launch {
            mapper.domainToUITimeWeather(getTimeWeatherUseCase(timeManager.urlTimeWeatherDate(), timeManager.urlTimeWeatherTime(), lat, lon)).collect { result ->
                _state.value = _state.value.copy(timeWeatherState = result)
            }
        }
    }

    private fun fetchWeekRainSky(regId: String) {
        val landCode = regId.landCodeGu(context = context)

        viewModelScope.launch {
            mapper.domainToUIWeekRainSky(getWeekRainSkyUseCase(landCode, timeManager.urlWeekWeatherTime())).collect { result ->
                _state.value = _state.value.copy(weekRainSkyState = result)
            }
        }
    }
}