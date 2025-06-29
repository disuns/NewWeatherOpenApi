package com.android.sj.presentation.models.state.viewstate

import com.android.sj.presentation.models.state.uistate.BaseUiState
import com.android.sj.presentation.utils.managers.LoadingStateManager

interface BaseViewState {
    fun getAllStates(): List<BaseUiState<out Any>>

    fun isAllLoading() {
        LoadingStateManager.isShow(getAllStates().any { it.isLoading })
    }
}