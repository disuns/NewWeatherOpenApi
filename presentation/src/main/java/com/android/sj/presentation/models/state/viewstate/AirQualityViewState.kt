package com.android.sj.presentation.models.state.viewstate

import com.android.sj.presentation.models.state.uistate.AirQualityUiState
import com.android.sj.presentation.models.state.uistate.BaseUiState
import com.android.sj.presentation.models.state.uistate.RltmStationUiState
import com.android.sj.presentation.models.state.uistate.StationFindUiState

data class AirQualityViewState(
    val airQualityUiState: AirQualityUiState = BaseUiState(),
    val rltmStationUiState: RltmStationUiState = BaseUiState(),
    val stationFindUiState: StationFindUiState = BaseUiState()
): BaseViewState {
    override fun getAllStates() = listOf(airQualityUiState, rltmStationUiState, stationFindUiState)
}
