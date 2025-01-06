package com.android.sj.presentation.models.state

import com.android.sj.domain.ApiResult
import com.android.sj.presentation.utils.managers.LoadingStateManager

interface BaseViewState {
    fun getAllStates(): List<ApiResult<*>>

    fun isAllLoading() {
        LoadingStateManager.isShow(getAllStates().any { it is ApiResult.Loading })
    }
}