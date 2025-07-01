package com.android.sj.presentation.models.state.viewstate

import com.android.sj.presentation.models.state.uistate.BaseUiState
import com.android.sj.presentation.models.state.uistate.TimeWeatherUIState
import com.android.sj.presentation.models.state.uistate.WeatherUIState
import com.android.sj.presentation.models.state.uistate.WeekRainySkyUIState

data class WeatherViewState(
    val weatherUiState : WeatherUIState = BaseUiState(),
    val timeWeatherUiState : TimeWeatherUIState = BaseUiState(),
    val weekRainSkyUiState : WeekRainySkyUIState = BaseUiState()
): BaseViewState {
    override fun getAllStates() = listOf(weatherUiState, timeWeatherUiState, weekRainSkyUiState)
}
