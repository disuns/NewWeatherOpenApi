package com.project.newweatheropenapi.dataclass.state

import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.utils.managers.LoadingStateManager

interface BaseViewState {
    fun getAllStates(): List<ApiResult<*>>

    fun isAllLoading() {
        LoadingStateManager.isShow(getAllStates().any { it is ApiResult.Loading })
    }
}