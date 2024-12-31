package com.android.sj.presentation.state

import com.android.sj.presentation.models.navermap.ReverseGeoUIData
import com.android.sj.domain.ApiResult

data class NaverMapViewState(
    val naverMapState : ApiResult<ReverseGeoUIData> = ApiResult.Loading
): BaseViewState {
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(naverMapState)
    }
}
