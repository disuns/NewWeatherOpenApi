package com.android.sj.presentation.mvi.partialstate

import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.models.uimodels.airquality.StationFindUIModel

sealed class AirQualityPartialState {
    object LoadingAirQuality : AirQualityPartialState()
    data class AirQualitySuccess(val data: AirQualityUiModel) : AirQualityPartialState()
    data class AirQualityError(val message: String, val code: Int? = null) : AirQualityPartialState()
    data class AirQualityEmpty(val message: String) : AirQualityPartialState()

    object LoadingRltmStation : AirQualityPartialState()
    data class RltmStationSuccess(val data: RltmStationUIModel) : AirQualityPartialState()
    data class RltmStationError(val message: String, val code: Int? = null) : AirQualityPartialState()
    data class RltmStationEmpty(val message: String) : AirQualityPartialState()

    object LoadingStationFind : AirQualityPartialState()
    data class StationFindSuccess(val data: StationFindUIModel) : AirQualityPartialState()
    data class StationFindError(val message: String, val code: Int? = null) : AirQualityPartialState()
    data class StationFindEmpty(val message: String) : AirQualityPartialState()
}