package com.android.sj.presentation.mvi.partialstate

import com.android.sj.presentation.models.uimodels.weather.TimeWeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeekRainSkyUIModel

sealed class WeatherPartialState {
    object LoadingWeather : WeatherPartialState()
    data class WeatherSuccess(val data: WeatherUIModel) : WeatherPartialState()
    data class WeatherError(val message: String, val code: Int? = null) : WeatherPartialState()

    object LoadingTimeWeather : WeatherPartialState()
    data class TimeWeatherSuccess(val data: TimeWeatherUIModel) : WeatherPartialState()
    data class TimeWeatherError(val message: String, val code: Int? = null) : WeatherPartialState()

    object LoadingWeekRainSky : WeatherPartialState()
    data class WeekRainSkySuccess(val data: WeekRainSkyUIModel) : WeatherPartialState()
    data class WeekRainSkyError(val message: String, val code: Int? = null) : WeatherPartialState()
}