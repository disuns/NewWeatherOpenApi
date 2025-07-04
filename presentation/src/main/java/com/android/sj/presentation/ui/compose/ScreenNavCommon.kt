package com.android.sj.presentation.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.common.event.UiEvent
import com.android.sj.presentation.common.sealed.ScreenRoute
import com.android.sj.presentation.common.ui.compose.btn.ScreenWithTopLocationButton
import com.android.sj.presentation.common.ui.compose.intro.IntroScreen
import com.android.sj.presentation.ui.compose.airQuality.AirQualityScreen
import com.android.sj.presentation.ui.compose.navermap.NaverMapScreen
import com.android.sj.presentation.ui.compose.weather.WeatherScreen
import com.android.sj.presentation.utils.navigateTo
import com.android.sj.presentation.utils.toastMessage
import com.android.sj.presentation.viewmodels.AirQualityViewModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import com.android.sj.presentation.viewmodels.WeatherViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun ScreenNavCommon (
    navController: NavHostController,
    locationDataManager: LocationDataManager,
    paddingValues: PaddingValues,
    naverMapVM: NaverMapViewModel,
    weatherVM: WeatherViewModel,
    airQualityVM: AirQualityViewModel,
    mergedEventEffect : Flow<UiEvent>,
    fetchAllWeather : (String, String, String) -> Unit,
    fetchAllAirQuality : (String, String) -> Unit,
    getLocation : () -> Unit,
    nowErrorFunc : (String, String) -> Unit,
    timeErrorFunc: (String, String) -> Unit,
    weekErrorFunc: (String) -> Unit,
    stationFindErrorFunc : (String, String) -> Unit,
    airQualityErrorFunc : () -> Unit
){
    val locationData = locationDataManager.locationData.collectAsState()
    val locationValue = locationData.value
    val address = locationValue.address

    val context = LocalContext.current

    LaunchedEffect(mergedEventEffect) {
        mergedEventEffect.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> toastMessage(event.message, context)
                is UiEvent.UpdateLocation -> locationDataManager.updateLocationData(
                    lat = event.lat, lon = event.lon,
                    address = event.address, x = event.x, y = event.y
                )
                is UiEvent.Navigate -> navigateTo(event.route, navController)
            }
        }
    }

    LaunchedEffect(address) {
        if (address.isNotEmpty()) {
            fetchAllWeather(locationValue.lat.toString(), locationValue.lng.toString(), address)
            fetchAllAirQuality(locationValue.x, locationValue.y)
        }
    }

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Intro.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = ScreenRoute.Intro.route) {
            IntroScreen(
                onNavigate = {
                    getLocation()
                    navigateTo(ScreenRoute.Intro, navController, true)
                })
        }
        composable(route = ScreenRoute.Weather.route) {
            ScreenWithTopLocationButton(
                onClick = { navigateTo(ScreenRoute.Weather, navController) },
                address = address
            ) { modifier ->
                WeatherScreen(modifier = modifier, viewModel = weatherVM,
                    nowErrorFunc = {
                        nowErrorFunc(locationValue.lat.toString(), locationValue.lng.toString())
                    },
                    timeErrorFunc = {
                        timeErrorFunc(locationValue.lat.toString(), locationValue.lng.toString())
                    },
                    weekErrorFunc = {
                        weekErrorFunc(address)
                    }
                )
            }
        }
        composable(route = ScreenRoute.AirQuality.route) {
            ScreenWithTopLocationButton(
                onClick = { navigateTo(ScreenRoute.AirQuality, navController) },
                address = address
            ) { modifier ->
                AirQualityScreen(
                    modifier = modifier,
                    viewModel = airQualityVM,
                    stationFindErrorFunc = {
                        stationFindErrorFunc(locationValue.x, locationValue.y)
                    },
                    airQualityErrorFunc = {
                        airQualityErrorFunc()
                    }
                )
            }
        }
        composable(route = ScreenRoute.NaverMap.route) {
            NaverMapScreen(
                locationDataManager = locationDataManager,
                viewModel = naverMapVM
            )
        }
    }
}