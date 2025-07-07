package com.android.sj.presentation.ui.compose.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.sj.presentation.intent.WeatherIntent
import com.android.sj.presentation.ui.compose.weather.weekweather.WeekWeatherColumnCommon
import com.android.sj.presentation.utils.LocalWeatherVM

@Composable
fun WeekWeatherColumn(modifier: Modifier) {
    val weatherVM = LocalWeatherVM.current

    WeekWeatherColumnCommon(modifier) { address ->
        weatherVM.sendIntent(
            WeatherIntent.LoadWeekRainSky(address)
        )
    }
}