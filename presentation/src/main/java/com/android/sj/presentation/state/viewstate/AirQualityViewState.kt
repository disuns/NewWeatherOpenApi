package com.android.sj.presentation.state.viewstate

import com.android.sj.presentation.state.uistate.AirQualityUiState
import com.android.sj.presentation.state.uistate.BaseUiState
import com.android.sj.presentation.state.uistate.RltmStationUiState
import com.android.sj.presentation.state.uistate.StationFindUiState

data class AirQualityViewState(
    val airQualityUiState: AirQualityUiState = BaseUiState(),
    val rltmStationUiState: RltmStationUiState = BaseUiState(),
    val stationFindUiState: StationFindUiState = BaseUiState()
): BaseViewState
