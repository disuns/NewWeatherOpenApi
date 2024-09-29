package com.project.newweatheropenapi.dataclass.state

import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeekRainSkyResponse

data class WeatherViewState(
    val weatherState : ApiResult<WeatherResponse> = ApiResult.Loading,
    val timeWeatherState : ApiResult<WeatherResponse> = ApiResult.Loading,
    val weekRainSkyState : ApiResult<WeekRainSkyResponse> = ApiResult.Loading
):BaseViewState{
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(weatherState, timeWeatherState, weekRainSkyState)
    }
}
