package com.android.sj.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.mappers.AirQualityPresentationMapper
import com.android.sj.presentation.models.state.AirQualityViewState
import com.android.sj.presentation.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val getAirQualityUseCase : GetAirQualityUseCase,
    private val getRltmStationUseCase : GetRltmStationUseCase,
    private val getStationFindUseCase : GetStationFindUseCase,
    private val mapper : AirQualityPresentationMapper
) : BaseViewModel<AirQualityViewState>(AirQualityViewState()) {

//    val airQualityState: StateFlow<ApiResult<AirQualityUiData>> = PresentationMapper
//        .domainToUIAirQuality(getAirQualityUseCase())
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            initialValue = ApiResult.Loading
//        )
//
//    val rltmStationState: StateFlow<ApiResult<RltmStationUIData>> = getRltmStationUseCase()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            initialValue = ApiResult.Loading
//        )
//
//    val stationFindState: StateFlow<ApiResult<StationFindUIData>> = getStationFindUseCase()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            initialValue = ApiResult.Loading
//        )
    fun handleIntent(intent: AirQualityIntent) {
        super.handleIntent(intent)
        when (intent) {
            is AirQualityIntent.LoadAllAirQuality -> fetchAllAirQualityData(
                intent.regionX,
                intent.regionY,
                intent.context
            )

            is AirQualityIntent.LoadAirQuality -> fetchAirQuality(intent.context)
            is AirQualityIntent.LoadRltmStation -> fetchRltmStation(intent.stationName)
            is AirQualityIntent.LoadStationFind -> fetchStationFindAndThenRltmStation(intent.regionX, intent.regionY)
        }
    }

    private fun fetchAllAirQualityData(
        regionX: String,
        regionY: String,
        context: Context
    ) {
        fetchAllData(
            { fetchAirQuality(context) },
            { fetchStationFindAndThenRltmStation(regionX, regionY) }
        )
    }

    private fun fetchStationFindAndThenRltmStation(regionX: String, regionY: String) {
        viewModelScope.launch {
            mapper.domainToUIStationFind(getStationFindUseCase(regionX, regionY)).collect { result ->
                _state.value = _state.value.copy(stationFindState = result)

                if (result is ApiResult.Success && result.value.stationName != "정보없음") {
                    fetchRltmStation(result.value.stationName)
                }
            }
        }
    }

    private fun fetchAirQuality(context: Context) {
        viewModelScope.launch {
            mapper.domainToUIAirQuality(getAirQualityUseCase(TimeManager(context).urlAirQualityDate())).collect{ result ->
                _state.value = _state.value.copy(airQualityState = result)
            }
        }
    }

    private fun fetchRltmStation(stationName: String) {
        viewModelScope.launch {
            mapper.domainToUIRltmStation(getRltmStationUseCase(stationName)).collect{ result ->
                _state.value = _state.value.copy(rltmStationState = result)
            }
        }
    }
}