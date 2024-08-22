package com.project.newweatheropenapi.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.newweatheropenapi.ComposeHelpManager
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ScreenRoute
import com.project.newweatheropenapi.ui.compose.fcst.FcstScreen
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
            IntroScreen(onNavigate = { navController.navigate(ScreenRoute.Weather.name) })
        }
        composable(route = ScreenRoute.Weather.name){
            WeatherScreen(onNavigate = { navController.navigate(ScreenRoute.Fcst.name) })
        }
        composable(route = ScreenRoute.Fcst.name){
            FcstScreen(onNavigate = { navController.navigate(ScreenRoute.NaverMap.name) })
        }
        composable(route = ScreenRoute.NaverMap.name){
            NaverMapScreen(onNavigate = { navController.navigate(ScreenRoute.Intro.name) })
        }
    }
}