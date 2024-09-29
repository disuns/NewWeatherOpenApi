package com.project.newweatheropenapi.dataclass.state

import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse

data class NaverMapViewState(
    val naverMapState : ApiResult<NaverMapResponse> = ApiResult.Loading
):BaseViewState{
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(naverMapState)
    }
}
