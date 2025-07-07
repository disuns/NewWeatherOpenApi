package com.android.sj.presentation.state.viewstate

import com.android.sj.presentation.state.uistate.BaseUiState
import com.android.sj.presentation.state.uistate.ReverseGeoUIState

data class NaverMapViewState(
    val naverMapUiState : ReverseGeoUIState = BaseUiState()
): BaseViewState
