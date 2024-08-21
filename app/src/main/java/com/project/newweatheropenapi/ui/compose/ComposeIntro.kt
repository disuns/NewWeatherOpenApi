package com.project.newweatheropenapi.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.newweatheropenapi.ComposeHelpManager
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ScreenRoute

@Composable
fun InitScreen() {
    val navController = rememberNavController()
    ScreenNav(navController = navController)
}

@Composable
fun ScreenNav(navController: NavHostController){
    NavHost(navController = navController, startDestination = ScreenRoute.Intro.name ) {
        composable(route = ScreenRoute.Intro.name){
            IntroScreen(navController)
        }
        composable(route = ScreenRoute.Weather.name){
            WeatherScreen(navController)
        }
        composable(route = ScreenRoute.Fcst.name){
            FcstScreen(navController)
        }
        composable(route = ScreenRoute.NaverMap.name){
            NaverMapScreen(navController)
        }
    }
}

@Composable
fun IntroScreen(navController: NavHostController) {    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val text = ComposeHelpManager.previewStringResource(R.string.loading,"Loading")
        val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.Loading, 30.0f ).sp

        Button(onClick = { navController.navigate(ScreenRoute.Weather.name)}) {
            
        }
        Text(
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun WeatherScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val text = ComposeHelpManager.previewStringResource(R.string.weather,"weather")
        val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.Loading, 30.0f ).sp

        Button(onClick = { navController.navigate(ScreenRoute.Fcst.name)}) {

        }

        Text(
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun FcstScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val text = ComposeHelpManager.previewStringResource(R.string.fcst,"fcst")
        val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.Loading, 30.0f ).sp

        Button(onClick = { navController.navigate(ScreenRoute.NaverMap.name)}) {

        }

        Text(
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun NaverMapScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val text = ComposeHelpManager.previewStringResource(R.string.navermap,"navermap")
        val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.Loading, 30.0f ).sp

        Button(onClick = { navController.navigate(ScreenRoute.Intro.name)}) {

        }

        Text(
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}