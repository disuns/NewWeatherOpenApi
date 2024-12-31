package com.android.sj.presentation.state

import com.android.sj.presentation.models.airquality.AirQualityUiData
import com.android.sj.presentation.models.airquality.RltmStationUIData
import com.android.sj.presentation.models.airquality.StationFindUIData
import com.android.sj.domain.ApiResult

data class AirQualityViewState(
    val airQualityState: ApiResult<AirQualityUiData> = ApiResult.Loading,
    val rltmStationState: ApiResult<RltmStationUIData> = ApiResult.Loading,
    val stationFindState: ApiResult<StationFindUIData> = ApiResult.Loading
): BaseViewState {
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(airQualityState, rltmStationState, stationFindState)
    }
}
