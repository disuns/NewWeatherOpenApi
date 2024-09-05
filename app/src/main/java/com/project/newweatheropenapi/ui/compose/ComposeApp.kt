package com.project.newweatheropenapi.ui.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.newweatheropenapi.enums.ScreenRoute
import com.project.newweatheropenapi.ui.compose.airQuality.AirQualityScreen
import com.project.newweatheropenapi.ui.compose.intro.IntroScreen
import com.project.newweatheropenapi.ui.compose.navermap.NaverMapScreen
import com.project.newweatheropenapi.ui.compose.weather.WeatherScreen
import com.project.newweatheropenapi.utils.Managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel

@Composable
fun InitScreen(
    locationDataManager: LocationDataManager,
    naverMapViewModel: NaverMapViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    naverMapViewModel.getLocation()

    ScreenNav(
        navController = navController,
        locationDataManager = locationDataManager,
        naverMapViewModel = naverMapViewModel
    )
}

@Composable
fun ScreenNav(
    navController: NavHostController,
    locationDataManager: LocationDataManager,
    naverMapViewModel: NaverMapViewModel
) {

    NavHost(navController = navController, startDestination = ScreenRoute.Intro.name) {
        composable(route = ScreenRoute.Intro.name) {
            IntroScreen(onNavigate = {
                navController.navigate(ScreenRoute.Weather.name) {
                    popUpTo(ScreenRoute.Intro.name) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }
        composable(route = ScreenRoute.Weather.name) {
//            WeatherScreen(onNavigate = { navController.navigate(ScreenRoute.AirQuality.name) }, locationDataManager = locationDataManager)
            WeatherScreen(
                onNavigate = { navController.navigate(ScreenRoute.NaverMap.name) },
                locationDataManager = locationDataManager
            )
        }
        composable(route = ScreenRoute.AirQuality.name) {
            AirQualityScreen(
                onNavigate = { navController.navigate(ScreenRoute.NaverMap.name) },
                locationDataManager = locationDataManager
            )
        }
        composable(route = ScreenRoute.NaverMap.name) {
            NaverMapScreen(
                onNavigate = { navController.navigate(ScreenRoute.Intro.name) },
                locationDataManager = locationDataManager,
                viewModel = naverMapViewModel
            )
        }
    }
}