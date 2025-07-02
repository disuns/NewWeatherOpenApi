package com.android.sj.presentation.common.state.viewstate

import com.android.sj.presentation.common.state.uistate.AirQualityUiState
import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.common.state.uistate.RltmStationUiState
import com.android.sj.presentation.common.state.uistate.StationFindUiState

data class AirQualityViewState(
    val airQualityUiState: AirQualityUiState = BaseUiState(),
    val rltmStationUiState: RltmStationUiState = BaseUiState(),
    val stationFindUiState: StationFindUiState = BaseUiState()
): BaseViewState {
    override fun getAllStates() = listOf(airQualityUiState, rltmStationUiState, stationFindUiState)
}
