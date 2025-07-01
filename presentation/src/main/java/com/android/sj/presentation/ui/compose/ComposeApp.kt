package com.android.sj.presentation.ui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.sj.common.utils.logMessage
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.event.UiEvent
import com.android.sj.presentation.sealed.ScreenRoute
import com.android.sj.presentation.ui.compose.airQuality.AirQualityScreen
import com.android.sj.presentation.ui.compose.bottomNavigationBar.BottomNavigationBar
import com.android.sj.presentation.ui.compose.intro.IntroScreen
import com.android.sj.presentation.ui.compose.navermap.NaverMapScreen
import com.android.sj.presentation.ui.compose.weather.WeatherScreen
import com.android.sj.presentation.utils.toastMessage
import com.android.sj.presentation.viewmodels.AirQualityViewModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import com.android.sj.presentation.viewmodels.WeatherViewModel
import kotlinx.coroutines.flow.merge

@Composable
fun InitScreen(
    locationDataManager: LocationDataManager
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute in listOf(ScreenRoute.Weather.route, ScreenRoute.AirQuality.route)) {
            BottomNavigationBar(navController)
        }
    }) { paddingValues ->
        ScreenNav(
            navController = navController,
            locationDataManager = locationDataManager,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun ScreenNav(
    navController: NavHostController,
    locationDataManager: LocationDataManager,
    paddingValues: PaddingValues,
    naverMapViewModel: NaverMapViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    airQualityViewModel: AirQualityViewModel = hiltViewModel()
) {
    val mergedEvents = remember {
        merge(
            naverMapViewModel.events,
            weatherViewModel.events,
            airQualityViewModel.events
        )
    }

    val locationData = locationDataManager.locationData.collectAsState()
    val locationValue = locationData.value
    val address = locationValue.address

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mergedEvents.collect{ event->
            when(event){
                is UiEvent.ShowToast -> {
                    toastMessage(event.message, context)
                }
                is UiEvent.UpdateLocation -> {
                    locationDataManager.updateLocationData(
                        lat = event.lat,
                        lon = event.lon,
                        address = event.address,
                        x = event.x,
                        y = event.y
                    )
                }
                is UiEvent.Navigate -> {
                    navigateTo(event.route, navController)
                }
            }

        }
    }

    LaunchedEffect(address) {
        if (address.isNotEmpty()) {
            with(locationValue) {
                weatherViewModel.fetchAllWeatherData(
                    nx = lat.toString(),
                    ny = lng.toString(),
                    address = address
                )
                airQualityViewModel.fetchAllAirQualityData(
                    regionX = x,
                    regionY = y
                )
            }
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
                    navigateTo(ScreenRoute.Intro, navController, true)
                    naverMapViewModel.getLocation()
                })
        }
        composable(route = ScreenRoute.Weather.route) {
            ScreenWithTopLocationButton(
                onClick = { navigateTo(ScreenRoute.Weather, navController) },
                address = address
            ) { modifier ->
                WeatherScreen(modifier = modifier, viewModel = weatherViewModel,
                    nowErrorFunc = {
                        weatherViewModel.fetchWeather(
                            locationValue.lat.toString(),
                            locationValue.lng.toString()
                        )
                    },
                    timeErrorFunc = {
                        weatherViewModel.fetchTimeWeather(
                            locationValue.lat.toString(),
                            locationValue.lng.toString()
                        )
                    },
                    weekErrorFunc = {
                        weatherViewModel.fetchWeekRainSky(address)
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
                    viewModel = airQualityViewModel,
                    stationFindErrorFunc = {
                        airQualityViewModel.fetchStationFindAndThenRltmStation(
                            locationValue.x,
                            locationValue.y
                        )
                    },
                    airQualityErrorFunc = {
                        airQualityViewModel.fetchAirQuality()
                    }
                )
            }
        }
        composable(route = ScreenRoute.NaverMap.route) {
            NaverMapScreen(
                locationDataManager = locationDataManager,
                viewModel = naverMapViewModel
            )
        }
    }
}

private fun navigateTo(
    destination: ScreenRoute,
    navController: NavHostController,
    isPopUpTo: Boolean = false
) {
    destination.destination?.let { route ->
        navController.navigate(route.route) {
            if (isPopUpTo) {
                popUpTo(destination.route) { inclusive = true }
            }
            launchSingleTop = true
        }
    }
}