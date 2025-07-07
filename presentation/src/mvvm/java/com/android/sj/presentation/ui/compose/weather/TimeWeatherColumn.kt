package com.android.sj.presentation.ui.compose.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.sj.presentation.ui.compose.weather.timeweather.TimeWeatherColumnCommon
import com.android.sj.presentation.utils.LocalWeatherVM

@Composable
fun TimeWeatherColumn(modifier: Modifier) {
    val weatherVM = LocalWeatherVM.current

    TimeWeatherColumnCommon(
        modifier = modifier,
        errorFunc = { lat, lon ->
            weatherVM.fetchTimeWeather( lat, lon )
        }
    )
}