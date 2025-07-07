package com.android.sj.presentation.ui.compose.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.sj.presentation.ui.compose.weather.nowweather.NowWeatherColumnCommon
import com.android.sj.presentation.utils.LocalWeatherVM

@Composable
fun NowWeatherColumn(modifier: Modifier) {
    val weatherVM = LocalWeatherVM.current

    NowWeatherColumnCommon(modifier) { lat , lon ->
        weatherVM.fetchWeather( lat, lon )
    }
}