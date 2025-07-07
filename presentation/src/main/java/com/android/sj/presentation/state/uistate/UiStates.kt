package com.android.sj.presentation.state.uistate

import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.models.uimodels.airquality.StationFindUIModel
import com.android.sj.presentation.models.uimodels.navermap.ReverseGeoUIModel
import com.android.sj.presentation.models.uimodels.weather.TimeWeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeekRainSkyUIModel

typealias AirQualityUiState = BaseUiState<AirQualityUiModel>
typealias RltmStationUiState = BaseUiState<RltmStationUIModel>
typealias StationFindUiState = BaseUiState<StationFindUIModel>

typealias ReverseGeoUIState = BaseUiState<ReverseGeoUIModel>

typealias WeatherUIState = BaseUiState<WeatherUIModel>
typealias TimeWeatherUIState = BaseUiState<TimeWeatherUIModel>
typealias WeekRainySkyUIState = BaseUiState<WeekRainSkyUIModel>