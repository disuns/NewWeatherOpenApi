package com.android.sj.presentation.common.state.viewstate

import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.common.state.uistate.TimeWeatherUIState
import com.android.sj.presentation.common.state.uistate.WeatherUIState
import com.android.sj.presentation.common.state.uistate.WeekRainySkyUIState

data class WeatherViewState(
    val weatherUiState : WeatherUIState = BaseUiState(),
    val timeWeatherUiState : TimeWeatherUIState = BaseUiState(),
    val weekRainSkyUiState : WeekRainySkyUIState = BaseUiState()
): BaseViewState
