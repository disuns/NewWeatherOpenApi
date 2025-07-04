package com.android.sj.presentation.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.viewmodels.AirQualityViewModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import com.android.sj.presentation.viewmodels.WeatherViewModel

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided")
}

val LocalLocationDataManager = staticCompositionLocalOf<LocationDataManager> {
    error("LocalLocationDataManager is not provided")
}

//viewmodels
val LocalWeatherVM = staticCompositionLocalOf<WeatherViewModel> {
    error("LocalWeatherVM is not provided")
}

val LocalAirQualityVM = staticCompositionLocalOf<AirQualityViewModel> {
    error("LocalAirQualityVM is not provided")
}

val LocalNaverMapVM = staticCompositionLocalOf<NaverMapViewModel> {
    error("LocalNaverMapVM is not provided")
}