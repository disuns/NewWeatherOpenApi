package com.android.sj.presentation.models.state

import com.android.sj.presentation.models.uimodels.weather.TimeWeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeekRainSkyUIModel
import com.android.sj.domain.ApiResult

data class WeatherViewState(
    val weatherState : ApiResult<WeatherUIModel> = ApiResult.Loading,
    val timeWeatherState : ApiResult<TimeWeatherUIModel> = ApiResult.Loading,
    val weekRainSkyState : ApiResult<WeekRainSkyUIModel> = ApiResult.Loading
): BaseViewState {
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(weatherState, timeWeatherState, weekRainSkyState)
    }
}
