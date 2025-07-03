package com.android.sj.presentation.common.state.uistate

import com.android.sj.domain.ApiResult
import com.android.sj.presentation.common.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.common.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.common.models.uimodels.airquality.StationFindUIModel
import com.android.sj.presentation.common.models.uimodels.navermap.ReverseGeoUIModel
import com.android.sj.presentation.common.models.uimodels.weather.TimeWeatherUIModel
import com.android.sj.presentation.common.models.uimodels.weather.WeatherUIModel
import com.android.sj.presentation.common.models.uimodels.weather.WeekRainSkyUIModel

typealias AirQualityUiState = BaseUiState<AirQualityUiModel>
typealias RltmStationUiState = BaseUiState<RltmStationUIModel>
typealias StationFindUiState = BaseUiState<StationFindUIModel>

typealias ReverseGeoUIState = BaseUiState<ReverseGeoUIModel>

typealias WeatherUIState = BaseUiState<WeatherUIModel>
typealias TimeWeatherUIState = BaseUiState<TimeWeatherUIModel>
typealias WeekRainySkyUIState = BaseUiState<WeekRainSkyUIModel>