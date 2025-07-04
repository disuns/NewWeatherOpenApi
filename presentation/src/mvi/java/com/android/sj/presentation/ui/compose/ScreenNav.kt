package com.android.sj.presentation.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.intent.WeatherIntent
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
            weatherVM.sendIntent(
                WeatherIntent.LoadAllWeather(nx = nx, ny = ny, address = address)
            )
        }
        override fun onNowError(lat: String, lon: String) {
            weatherVM.sendIntent(
                WeatherIntent.LoadWeather(lat, lon )
            )
        }
        override fun onTimeError(lat: String, lon: String) {
            weatherVM.sendIntent(
                WeatherIntent.LoadTimeWeather(lat, lon)
            )
        }
        override fun onWeekError(address: String) {
            weatherVM.sendIntent(
                WeatherIntent.LoadWeekRainSky(address)
            )
        }
    }

    val airQualityHandler = object : ScreenNavAirQualityHandler {
        override fun fetchAll(x: String, y: String) {
            airQualityVM.sendIntent(
                AirQualityIntent.LoadAllAirQuality(regionX = x, regionY = y)
            )
        }
        override fun onStationError(x: String, y: String) {
            airQualityVM.sendIntent(
                AirQualityIntent.LoadStationFind(x, y)
            )
        }
        override fun onAirQualityError() {
            airQualityVM.sendIntent(
                AirQualityIntent.LoadAirQuality
            )
        }
    }

    ScreenNavCommon(
        paddingValues = paddingValues,
        mergedEventEffect = remember(naverMapVM, weatherVM, airQualityVM) {
            merge(naverMapVM.effects, weatherVM.effects, airQualityVM.effects)
        },
        getLocation = {
            naverMapVM.sendIntent(NaverMapIntent.GetLocation)
        },
        weatherHandler,
        airQualityHandler
    )
}