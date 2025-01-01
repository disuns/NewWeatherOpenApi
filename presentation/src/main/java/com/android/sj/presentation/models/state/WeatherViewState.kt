package com.android.sj.presentation.models.state

import com.android.sj.presentation.models.uimodels.weather.TimeWeatherUIData
import com.android.sj.presentation.models.uimodels.weather.WeatherUIData
import com.android.sj.presentation.models.uimodels.weather.WeekRainSkyUIData
import com.android.sj.domain.ApiResult

data class WeatherViewState(
    val weatherState : ApiResult<WeatherUIData> = ApiResult.Loading,
    val timeWeatherState : ApiResult<TimeWeatherUIData> = ApiResult.Loading,
    val weekRainSkyState : ApiResult<WeekRainSkyUIData> = ApiResult.Loading
): BaseViewState {
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(weatherState, timeWeatherState, weekRainSkyState)
    }
}
