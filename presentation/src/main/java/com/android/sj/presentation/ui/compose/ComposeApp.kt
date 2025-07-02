package com.android.sj.presentation.ui.compose

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
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.common.event.UiEvent
import com.android.sj.presentation.sealed.ScreenRoute
import com.android.sj.presentation.ui.compose.airQuality.AirQualityScreen
import com.android.sj.presentation.ui.compose.bottomNavigationBar.BottomNavigationBar
import com.android.sj.presentation.ui.compose.intro.IntroScreen
import com.android.sj.presentation.ui.compose.navermap.NaverMapScreen
import com.android.sj.presentation.ui.compose.weather.WeatherScreen
import com.android.sj.presentation.utils.toastMessage
import com.android.sj.presentation.mvvm.viewmodels.AirQualityMvvmViewModel
import com.android.sj.presentation.mvvm.viewmodels.NaverMapMvvmViewModel
import com.android.sj.presentation.mvvm.viewmodels.WeatherMvvmViewModel
import com.android.sj.presentation.ui.compose.loading.DialogScreen
import com.android.sj.presentation.utils.managers.LoadingStateManager
import kotlinx.coroutines.flow.merge

@Composable
fun InitScreen(
    locationDataManager: LocationDataManager
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isLoading = LoadingStateManager.isLoading.collectAsState().value

    if (isLoading && currentRoute != ScreenRoute.Intro.route) {
        DialogScreen()
    }
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
    naverMapMvvmViewModel: NaverMapMvvmViewModel = hiltViewModel(),
    weatherMvvmViewModel: WeatherMvvmViewModel = hiltViewModel(),
    airQualityMvvmViewModel: AirQualityMvvmViewModel = hiltViewModel()
) {
    val mergedEvents = remember {
        merge(
            naverMapMvvmViewModel.events,
            weatherMvvmViewModel.events,
            airQualityMvvmViewModel.events
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
                weatherMvvmViewModel.fetchAllWeatherData(
                    nx = lat.toString(),
                    ny = lng.toString(),
                    address = address
                )
                airQualityMvvmViewModel.fetchAllAirQualityData(
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
                    naverMapMvvmViewModel.getLocation()
                    navigateTo(ScreenRoute.Intro, navController, true)
                })
        }
        composable(route = ScreenRoute.Weather.route) {
            ScreenWithTopLocationButton(
                onClick = { navigateTo(ScreenRoute.Weather, navController) },
                address = address
            ) { modifier ->
                WeatherScreen(modifier = modifier, viewModel = weatherMvvmViewModel,
                    nowErrorFunc = {
                        weatherMvvmViewModel.fetchWeather(
                            locationValue.lat.toString(),
                            locationValue.lng.toString()
                        )
                    },
                    timeErrorFunc = {
                        weatherMvvmViewModel.fetchTimeWeather(
                            locationValue.lat.toString(),
                            locationValue.lng.toString()
                        )
                    },
                    weekErrorFunc = {
                        weatherMvvmViewModel.fetchWeekRainSky(address)
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
                    viewModel = airQualityMvvmViewModel,
                    stationFindErrorFunc = {
                        airQualityMvvmViewModel.fetchStationFindAndThenRltmStation(
                            locationValue.x,
                            locationValue.y
                        )
                    },
                    airQualityErrorFunc = {
                        airQualityMvvmViewModel.fetchAirQuality()
                    }
                )
            }
        }
        composable(route = ScreenRoute.NaverMap.route) {
            NaverMapScreen(
                locationDataManager = locationDataManager,
                viewModel = naverMapMvvmViewModel
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