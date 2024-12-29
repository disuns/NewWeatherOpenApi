package com.project.newweatheropenapi.dataclass.state

import com.project.newweatheropenapi.network.ApiResult
import com.android.sj.data.remote.response.navermap.NaverMapResponse

data class NaverMapViewState(
    val naverMapState : ApiResult<NaverMapResponse> = ApiResult.Loading
):BaseViewState{
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(naverMapState)
    }
}
