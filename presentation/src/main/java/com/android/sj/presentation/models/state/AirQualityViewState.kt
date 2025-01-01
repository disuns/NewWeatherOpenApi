package com.android.sj.presentation.models.state

import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiData
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIData
import com.android.sj.presentation.models.uimodels.airquality.StationFindUIData
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
