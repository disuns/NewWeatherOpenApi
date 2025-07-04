package com.android.sj.presentation.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.android.sj.presentation.interfaces.ui.ScreenNavAirQualityHandler
import com.android.sj.presentation.interfaces.ui.ScreenNavWeatherHandler
import com.android.sj.presentation.ui.compose.nav.ScreenNavCommon
import com.android.sj.presentation.utils.LocalAirQualityVM
import com.android.sj.presentation.utils.LocalNaverMapVM
import com.android.sj.presentation.utils.LocalWeatherVM
import kotlinx.coroutines.flow.merge

@Composable
fun ScreenNav(paddingValues: PaddingValues) {
    val naverMapVM = LocalNaverMapVM.current
    val weatherVM = LocalWeatherVM.current
    val airQualityVM = LocalAirQualityVM.current

    val weatherHandler = object : ScreenNavWeatherHandler {
        override fun fetchAll(nx: String, ny: String, address: String) {
            weatherVM.fetchAllWeatherData(nx = nx, ny = ny, address = address)
        }
        override fun onNowError(lat: String, lon: String) {
            weatherVM.fetchWeather( lat, lon )
        }
        override fun onTimeError(lat: String, lon: String) {
            weatherVM.fetchTimeWeather( lat, lon )
        }
        override fun onWeekError(address: String) {
            weatherVM.fetchWeekRainSky(address)
        }
    }

    val airQualityHandler = object : ScreenNavAirQualityHandler{
        override fun fetchAll(x: String, y: String) {
            airQualityVM.fetchAllAirQualityData(regionX = x, regionY = y)
        }
        override fun onStationError(x: String, y: String) {
            airQualityVM.fetchStationFindAndThenRltmStation(x, y)
        }
        override fun onAirQualityError() {
            airQualityVM.fetchAirQuality()
        }
    }

    ScreenNavCommon(
        paddingValues = paddingValues,
        mergedEventEffect = remember(naverMapVM, weatherVM, airQualityVM) {
            merge(naverMapVM.events, weatherVM.events, airQualityVM.events)
        },
        getLocation = {
            naverMapVM.getLocation()
        },
        weatherHandler,
        airQualityHandler
    )
}