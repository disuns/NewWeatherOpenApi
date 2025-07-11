package com.android.sj.presentation.ui.compose.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.sj.presentation.event.UiEvent
import com.android.sj.presentation.sealed.ScreenRoute
import com.android.sj.presentation.ui.compose.airQuality.AirQualityScreen
import com.android.sj.presentation.ui.compose.btn.ScreenWithTopLocationButton
import com.android.sj.presentation.ui.compose.intro.IntroScreen
import com.android.sj.presentation.ui.compose.navermap.NaverMapScreen
import com.android.sj.presentation.ui.compose.weather.WeatherScreen
import com.android.sj.presentation.utils.LocalLocationDataManager
import com.android.sj.presentation.utils.LocalNavController
import com.android.sj.presentation.utils.navigateTo
import com.android.sj.presentation.utils.toastMessage
import kotlinx.coroutines.flow.Flow

@Composable
fun ScreenNavCommon (
    paddingValues: PaddingValues,
    mergedEventEffect: Flow<UiEvent>,
    getLocation: () -> Unit,
    weatherFetchAll: (String, String, String)->Unit,
    airQualityFetchAll: (String, String)->Unit,
){
    val navController = LocalNavController.current
    val locationDataManager = LocalLocationDataManager.current

    val locationValue by locationDataManager.locationData.collectAsStateWithLifecycle()
    val address by remember{
        derivedStateOf {
            locationValue.address
        }
    }

    HandleFetchAllData(address){
        weatherFetchAll(locationValue.lat.toString(), locationValue.lng.toString(), address)
        airQualityFetchAll(locationValue.x, locationValue.y)
    }
    HandleUIEventOrEffect(mergedEventEffect)

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
                WeatherScreen(modifier = modifier)
            }
        }
        composable(route = ScreenRoute.AirQuality.route) {
            ScreenWithTopLocationButton(
                onClick = { navigateTo(ScreenRoute.AirQuality, navController) },
                address = address
            ) { modifier ->
                AirQualityScreen(modifier = modifier)
            }
        }
        composable(route = ScreenRoute.NaverMap.route) {
            NaverMapScreen()
        }
    }
}

@Composable
fun HandleUIEventOrEffect(
    mergedEventEffect: Flow<UiEvent>
) {
    val context = LocalContext.current
    val currentEvent by mergedEventEffect.collectAsStateWithLifecycle(initialValue = null)

    val locationDataManager = LocalLocationDataManager.current
    val navController = LocalNavController.current

    LaunchedEffect(currentEvent) {
        currentEvent.let { event ->
            when (event) {
                is UiEvent.ShowToast -> toastMessage(event.message, context)
                is UiEvent.UpdateLocation -> locationDataManager.updateLocationData(
                    lat = event.lat, lon = event.lon,
                    address = event.address, x = event.x, y = event.y
                )
                is UiEvent.Navigate -> navigateTo(event.route, navController)
                null -> {}
            }
        }
    }
}

@Composable
fun HandleFetchAllData(address: String, onLocationReady: () -> Unit) {
    LaunchedEffect(address) {
        if (address.isNotEmpty()) onLocationReady()
    }
}