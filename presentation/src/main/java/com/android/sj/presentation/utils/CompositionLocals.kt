package com.android.sj.presentation.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.viewmodels.AirQualityViewModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import com.android.sj.presentation.viewmodels.WeatherViewModel

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}

val LocalLocationDataManager = compositionLocalOf<LocationDataManager> {
    error("LocalLocationDataManager is not provided")
}

//viewmodels
val LocalWeatherVM = compositionLocalOf<WeatherViewModel> {
    error("LocalWeatherVM is not provided")
}

val LocalAirQualityVM = compositionLocalOf<AirQualityViewModel> {
    error("LocalAirQualityVM is not provided")
}

val LocalNaverMapVM = compositionLocalOf<NaverMapViewModel> {
    error("LocalNaverMapVM is not provided")
}