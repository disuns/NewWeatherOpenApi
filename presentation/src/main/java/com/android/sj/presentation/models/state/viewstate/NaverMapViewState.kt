package com.android.sj.presentation.models.state.viewstate

import com.android.sj.presentation.models.state.uistate.BaseUiState
import com.android.sj.presentation.models.state.uistate.ReverseGeoUIState

data class NaverMapViewState(
    val naverMapUiState : ReverseGeoUIState = BaseUiState()
): BaseViewState {
    override fun getAllStates() = listOf(naverMapUiState)
}
