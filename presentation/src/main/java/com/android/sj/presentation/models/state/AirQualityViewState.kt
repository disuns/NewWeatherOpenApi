package com.android.sj.presentation.models.state

import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.models.uimodels.airquality.StationFindUIModel
import com.android.sj.domain.ApiResult

data class AirQualityViewState(
    val airQualityState: ApiResult<AirQualityUiModel> = ApiResult.Loading,
    val rltmStationState: ApiResult<RltmStationUIModel> = ApiResult.Loading,
    val stationFindState: ApiResult<StationFindUIModel> = ApiResult.Loading
): BaseViewState {
    override fun getAllStates(): List<ApiResult<*>> {
        return listOf(airQualityState, rltmStationState, stationFindState)
    }
}
