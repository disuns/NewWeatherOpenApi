package com.project.newweatheropenapi.ui.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.newweatheropenapi.ScreenRoute
import com.project.newweatheropenapi.ui.compose.airQuality.AirQualityScreen
import com.project.newweatheropenapi.ui.compose.intro.IntroScreen
import com.project.newweatheropenapi.ui.compose.navermap.NaverMapScreen
import com.project.newweatheropenapi.ui.compose.weather.WeatherScreen

@Composable
fun InitScreen() {
    val navController = rememberNavController()
    ScreenNav(navController = navController)
}

@Composable
fun ScreenNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = ScreenRoute.Intro.name ) {
        composable(route = ScreenRoute.Intro.name){
            IntroScreen(onNavigate = { navController.navigate(ScreenRoute.Weather.name) {
                popUpTo(ScreenRoute.Intro.name){inclusive = true}
                launchSingleTop = true
            } })
        }
        composable(route = ScreenRoute.Weather.name){
            WeatherScreen(onNavigate = { navController.navigate(ScreenRoute.AirQuality.name) })
        }
        composable(route = ScreenRoute.AirQuality.name){
            AirQualityScreen(onNavigate = { navController.navigate(ScreenRoute.NaverMap.name) })
        }
        composable(route = ScreenRoute.NaverMap.name){
            NaverMapScreen(onNavigate = { navController.navigate(ScreenRoute.Intro.name) })
        }
    }
}