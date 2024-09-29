package com.project.newweatheropenapi.sealed.intent

import android.content.Context

sealed class WeatherIntent {
    data class LoadAllWeather(val nx: String, val ny: String, val address: String) : WeatherIntent()
    data class LoadWeather(val nx: String, val ny: String) : WeatherIntent()
    data class LoadTimeWeather(val nx: String, val ny: String) : WeatherIntent()
    data class LoadWeekRainSky(val address: String) : WeatherIntent()
}