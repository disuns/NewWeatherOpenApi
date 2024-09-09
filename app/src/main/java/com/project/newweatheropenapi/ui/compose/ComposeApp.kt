package com.project.newweatheropenapi.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.project.newweatheropenapi.utils.Managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel

@Composable
fun InitScreen(
    locationDataManager: LocationDataManager,
    naverMapViewModel: NaverMapViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    naverMapViewModel.getLocation()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute in listOf(ScreenRoute.Weather.route, ScreenRoute.AirQuality.route)) {
            BottomNavigationBar(navController)
        }
    }) { paddingValues ->
        ScreenNav(
            navController = navController,
            locationDataManager = locationDataManager,
            naverMapViewModel = naverMapViewModel,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun ScreenNav(
    navController: NavHostController,
    locationDataManager: LocationDataManager,
    naverMapViewModel: NaverMapViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Intro.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = ScreenRoute.Intro.route) {
            IntroScreen(
                onNavigate = {
                    navigateTo(ScreenRoute.Intro, navController, true)
                })
        }
        composable(route = ScreenRoute.Weather.route) {
            WeatherScreen(
                onNavigate = { navigateTo(ScreenRoute.Weather, navController) },
                locationDataManager = locationDataManager
            )
        }
        composable(route = ScreenRoute.AirQuality.route) {
            AirQualityScreen(
                onNavigate = { navigateTo(ScreenRoute.AirQuality, navController) },
                locationDataManager = locationDataManager
            )
        }
        composable(route = ScreenRoute.NaverMap.route) {
            NaverMapScreen(
                onNavigate = { navigateTo(ScreenRoute.NaverMap, navController) },
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