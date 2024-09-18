package com.project.newweatheropenapi.ui.compose.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    onNavigate: () -> Unit = {},
    viewModel: WeatherViewModel = hiltViewModel(),
    locationDataManager: LocationDataManager
) {
    val locationData = locationDataManager.locationData.collectAsState()
    val address = locationData.value.address

    LaunchedEffect(address) {
        if (address.isNotEmpty()) {
            viewModel.fetchAllWeatherData(
                nx = locationData.value.latLng.latitude.toString(),
                ny = locationData.value.latLng.longitude.toString(),
                address = address
            )
        }
    }

    WeatherScreenUI(onNavigate, address, viewModel)
}