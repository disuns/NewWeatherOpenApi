package com.android.sj.presentation.common.state.viewstate

import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.utils.managers.LoadingStateManager

interface BaseViewState {
    fun getAllStates(): List<BaseUiState<out Any>>

    fun isAllLoading() {
        LoadingStateManager.isShow(getAllStates().any { it.isLoading })
    }
}