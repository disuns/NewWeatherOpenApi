package com.project.newweatheropenapi.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.newweatheropenapi.sealed.ScreenRoute
import com.project.newweatheropenapi.ui.compose.airQuality.AirQualityScreen
import com.project.newweatheropenapi.ui.compose.bottomNavigationBar.BottomNavigationBar
import com.project.newweatheropenapi.ui.compose.intro.IntroScreen
import com.project.newweatheropenapi.ui.compose.navermap.NaverMapScreen
import com.project.newweatheropenapi.ui.compose.weather.WeatherScreen
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.AirQualityViewModel
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

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
    val locationData = locationDataManager.locationData.collectAsState()
    val locationValue = locationData.value
    val address = locationValue.address

    val context = LocalContext.current

    LaunchedEffect(address) {
        if (address.isNotEmpty()) {
            with(locationValue) {
                weatherViewModel.fetchAllWeatherData(
                    nx = latLng.latitude.toString(),
                    ny = latLng.longitude.toString(),
                    address = address
                )
                airQualityViewModel.fetchAllAirQualityData(
                    regionX = x,
                    regionY = y,
                    context
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
                onClick = {navigateTo(ScreenRoute.Weather, navController)},
                address = address
            ) {modifier ->
                WeatherScreen(modifier = modifier, viewModel = weatherViewModel)
            }
        }
        composable(route = ScreenRoute.AirQuality.route) {
            ScreenWithTopLocationButton(
                onClick = {navigateTo(ScreenRoute.AirQuality, navController)},
                address = address
            ) {modifier ->
                AirQualityScreen(
                    modifier = modifier,
                    viewModel = airQualityViewModel
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
            if(isPopUpTo) {
                popUpTo(destination.route) { inclusive = true }
            }
            launchSingleTop = true
        }
    }
}