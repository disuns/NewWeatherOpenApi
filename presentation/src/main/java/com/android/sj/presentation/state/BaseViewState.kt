package com.android.sj.presentation.state

import com.android.sj.domain.ApiResult
import com.android.sj.presentation.managers.LoadingStateManager

interface BaseViewState {
    fun getAllStates(): List<ApiResult<*>>

    fun isAllLoading() {
        LoadingStateManager.isShow(getAllStates().any { it is ApiResult.Loading })
    }
}