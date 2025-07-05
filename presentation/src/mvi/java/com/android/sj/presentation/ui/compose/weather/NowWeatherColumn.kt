package com.android.sj.presentation.ui.compose.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.sj.presentation.intent.WeatherIntent
import com.android.sj.presentation.ui.compose.weather.nowweather.NowWeatherColumnCommon
import com.android.sj.presentation.utils.LocalWeatherVM

@Composable
fun NowWeatherColumn(modifier: Modifier) {
    val weatherVM = LocalWeatherVM.current

    NowWeatherColumnCommon(modifier) { lat, lon ->
        weatherVM.sendIntent(
            WeatherIntent.LoadWeather(lat, lon )
        )
    }
}