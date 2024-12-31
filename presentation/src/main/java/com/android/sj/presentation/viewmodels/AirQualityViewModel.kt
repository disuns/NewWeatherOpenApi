package com.android.sj.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.common.utils.logMessage
import com.android.sj.domain.ApiResult
import com.android.sj.domain.usecase.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.airquality.GetStationFindUseCase
import com.android.sj.presentation.RequestConstants.AIR_CODE
import com.android.sj.presentation.RequestConstants.DATA_POTAL_SERVICE_KEY
import com.android.sj.presentation.RequestConstants.DATA_TYPE_LOWER
import com.android.sj.presentation.RequestConstants.DATE_TERM
import com.android.sj.presentation.RequestConstants.NUM_OF_ROWS_AIR
import com.android.sj.presentation.RequestConstants.PAGE_NO_DEFAULT
import com.android.sj.presentation.RequestConstants.RLTM_DATA_VERSION
import com.android.sj.presentation.RequestConstants.STATION_VERSION
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.mappers.PresentationMapper
import com.android.sj.presentation.models.request.datapotal.AirQualityRequest
import com.android.sj.presentation.models.request.datapotal.RltmStationRequest
import com.android.sj.presentation.models.request.datapotal.StationFindRequest
import com.android.sj.presentation.models.request.datapotal.toMap
import com.android.sj.presentation.state.AirQualityViewState
import com.android.sj.presentation.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val getAirQualityUseCase : GetAirQualityUseCase,
    private val getRltmStationUseCase : GetRltmStationUseCase,
    private val getStationFindUseCase : GetStationFindUseCase,
    private val mapper : PresentationMapper
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
            val request = StationFindRequest(
                DATA_POTAL_SERVICE_KEY, DATA_TYPE_LOWER, regionX, regionY, STATION_VERSION
            )

            mapper.domainToUIStationFind(getStationFindUseCase(request.toMap())).collect { result ->
                _state.value = _state.value.copy(stationFindState = result)

                if (result is ApiResult.Success && result.value.stationName != "정보없음") {
                    fetchRltmStation(result.value.stationName)
                }
            }
        }
    }

    private fun fetchAirQuality(context: Context) {
        viewModelScope.launch {
            val request = AirQualityRequest(
                DATA_POTAL_SERVICE_KEY,
                DATA_TYPE_LOWER,
                PAGE_NO_DEFAULT,
                NUM_OF_ROWS_AIR,
                TimeManager(context).urlAirQualityDate(),
                AIR_CODE
            )
            mapper.domainToUIAirQuality(getAirQualityUseCase(request.toMap())).collect{ result ->
                _state.value = _state.value.copy(airQualityState = result)
            }
        }
    }

    private fun fetchRltmStation(stationName: String) {
        viewModelScope.launch {
            val request = RltmStationRequest(
                DATA_POTAL_SERVICE_KEY,
                DATA_TYPE_LOWER,
                PAGE_NO_DEFAULT,
                NUM_OF_ROWS_AIR,
                stationName,
                DATE_TERM,
                RLTM_DATA_VERSION
            )
            logMessage("request : $request")
            mapper.domainToUIRltmStation(getRltmStationUseCase(request.toMap())).collect{ result ->
                _state.value = _state.value.copy(rltmStationState = result)
            }
        }
    }
}