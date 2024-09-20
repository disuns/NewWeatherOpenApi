package com.project.newweatheropenapi.ui.compose.airQuality

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.AirQualityViewModel

@Composable
fun AirQualityScreen(
    modifier: Modifier,
    viewModel: AirQualityViewModel = hiltViewModel(),
    locationDataManager: LocationDataManager
) {
    val context = LocalContext.current
    val locationData = locationDataManager.locationData.collectAsState()
    val locationValue = locationData.value

    LaunchedEffect(locationValue) {
        viewModel.fetchAllAirQualityData(
            regionX = locationData.value.x,
            regionY = locationData.value.y,
            context
        )
    }

    AirQualityScreenUI(modifier, viewModel)
}