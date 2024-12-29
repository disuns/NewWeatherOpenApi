package com.project.newweatheropenapi.dataclass.state

import com.project.newweatheropenapi.network.ApiResult
import com.android.sj.data.remote.response.datapotal.AirQualityResponse
import com.android.sj.data.remote.response.datapotal.RltmStationResponse
import com.android.sj.data.remote.response.datapotal.StationFindResponse

data class AirQualityViewState(
    val airQualityState: ApiResult<AirQualityResponse> = ApiResult.Loading,
    val rltmStationState: ApiResult<RltmStationResponse> = ApiResult.Loading,
    val stationFindState: ApiResult<StationFindResponse> = ApiResult.Loading
):BaseViewState{
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(airQualityState, rltmStationState, stationFindState)
    }
}
