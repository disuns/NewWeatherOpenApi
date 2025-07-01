package com.android.sj.presentation.mvvm.viewmodels

import android.content.Context
import com.android.sj.domain.usecase.usecaseinterface.weather.GetTimeWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeekRainSkyUseCase
import com.android.sj.presentation.mappers.WeatherPresentationMapper
import com.android.sj.presentation.models.state.viewstate.WeatherViewState
import com.android.sj.presentation.utils.convertGRIDGPS
import com.android.sj.presentation.utils.landCodeGu
import com.android.sj.presentation.utils.managers.TimeManager
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getTimeWeatherUseCase: GetTimeWeatherUseCase,
    private val getWeekRainSkyUseCase: GetWeekRainSkyUseCase,
    private val mapper : WeatherPresentationMapper,
    private val timeManager: TimeManager,
    @ApplicationContext val context: Context
) : BaseViewModel<WeatherViewState>(WeatherViewState()) {
    fun fetchAllWeatherData(
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

    fun fetchWeather(
        nx: String,
        ny: String
    ) {
        val (lat, lon) = convertCoordinates(nx, ny)

        fetchData(
            usecase = getWeatherUseCase(timeManager.urlNowDate(), timeManager.urlNowTime(), lat, lon),
            mapper = mapper::domainToUIWeather,
        ){ ui->
            copy(weatherUiState = ui)
        }
    }

    fun fetchTimeWeather(
        nx: String,
        ny: String
    ) {
        val (lat, lon) = convertCoordinates(nx, ny)

        fetchData(
            usecase = getTimeWeatherUseCase(timeManager.urlTimeWeatherDate(), timeManager.urlTimeWeatherTime(), lat, lon),
            mapper = mapper::domainToUITimeWeather
        ){ui->
            copy(timeWeatherUiState = ui)

        }
    }

    fun fetchWeekRainSky(regId: String) {
        val landCode = regId.landCodeGu(context = context)

        fetchData(
            usecase = getWeekRainSkyUseCase(landCode, timeManager.urlWeekWeatherTime()),
            mapper = mapper::domainToUIWeekRainSky
        ){ui->
            copy(weekRainSkyUiState = ui)

        }
    }

    private fun convertCoordinates(nx: String, ny: String): Pair<String, String> {
        val convertLatLng = LatLng(nx.toDouble(), ny.toDouble()).convertGRIDGPS(0)
        val lat = convertLatLng.latitude.toInt().toString()
        val lon = convertLatLng.longitude.toInt().toString()
        return Pair(lat, lon)
    }
}