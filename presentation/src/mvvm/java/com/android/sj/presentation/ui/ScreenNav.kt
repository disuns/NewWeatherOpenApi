package com.android.sj.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.ui.compose.ScreenNavCommon
import com.android.sj.presentation.viewmodels.AirQualityViewModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import com.android.sj.presentation.viewmodels.WeatherViewModel
import kotlinx.coroutines.flow.merge

@Composable
fun ScreenNav(
    navController: NavHostController,
    locationDataManager: LocationDataManager,
    paddingValues: PaddingValues
) {
    val naverMapVM = hiltViewModel<NaverMapViewModel>()
    val weatherVM = hiltViewModel<WeatherViewModel>()
    val airQualityVM = hiltViewModel<AirQualityViewModel>()

    ScreenNavCommon(
        navController = navController,
        locationDataManager = locationDataManager,
        paddingValues = paddingValues,
        naverMapVM = naverMapVM,
        weatherVM = weatherVM,
        airQualityVM = airQualityVM,
        mergedEventEffect = remember(naverMapVM, weatherVM, airQualityVM) {
            merge(naverMapVM.events, weatherVM.events, airQualityVM.events)
        },
        fetchAllWeather = { nx, ny, address ->
            weatherVM.fetchAllWeatherData(nx = nx, ny = ny, address = address)
        },
        fetchAllAirQuality = { x, y ->
            airQualityVM.fetchAllAirQualityData(regionX = x, regionY = y)
        },
        getLocation = {
            naverMapVM.getLocation()
        },
        nowErrorFunc = { lat, lon ->
            weatherVM.fetchWeather( lat, lon )
        },
        timeErrorFunc = { lat, lon ->
            weatherVM.fetchTimeWeather( lat, lon )
        },
        weekErrorFunc = { address ->
            weatherVM.fetchWeekRainSky(address)
        },
        stationFindErrorFunc = { x, y ->
            airQualityVM.fetchStationFindAndThenRltmStation(x, y)
        },
        airQualityErrorFunc = {
            airQualityVM.fetchAirQuality()
        }
    )
}