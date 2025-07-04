package com.android.sj.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.intent.WeatherIntent
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
            merge(naverMapVM.effects, weatherVM.effects, airQualityVM.effects)
        },
        fetchAllWeather = { nx, ny, address ->
            weatherVM.sendIntent(
                WeatherIntent.LoadAllWeather(nx = nx, ny = ny, address = address)
            )
        },
        fetchAllAirQuality = { x, y ->
            airQualityVM.sendIntent(
                AirQualityIntent.LoadAllAirQuality(regionX = x, regionY = y)
            )
        },
        getLocation = {
            naverMapVM.sendIntent(NaverMapIntent.GetLocation)
        },
        nowErrorFunc = { lat, lon ->
            weatherVM.sendIntent(
                WeatherIntent.LoadWeather(lat, lon )
            )
        },
        timeErrorFunc = { lat, lon ->
            weatherVM.sendIntent(
                WeatherIntent.LoadTimeWeather(lat, lon)
            )
        },
        weekErrorFunc = { address ->
            weatherVM.sendIntent(
                WeatherIntent.LoadWeekRainSky(address)
            )
        },
        stationFindErrorFunc = { x, y ->
            airQualityVM.sendIntent(
                AirQualityIntent.LoadStationFind(x, y)
            )
        },
        airQualityErrorFunc = {
            airQualityVM.sendIntent(
                AirQualityIntent.LoadAirQuality
            )
        }
    )
}