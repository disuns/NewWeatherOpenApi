package com.android.sj.presentation.common.state.viewstate

import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.common.state.uistate.ReverseGeoUIState

data class NaverMapViewState(
    val naverMapUiState : ReverseGeoUIState = BaseUiState()
): BaseViewState {
    override fun getAllStates() = listOf(naverMapUiState)
}
