package com.project.newweatheropenapi.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.project.newweatheropenapi.dataclass.state.AirQualityViewState
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.datapotal.AirQualityRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.RltmStationRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.StationFindRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.toMap
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.RltmStationResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.StationFindResponse
import com.project.newweatheropenapi.network.repository.AirQualityRepository
import com.project.newweatheropenapi.sealed.intent.AirQualityIntent
import com.project.newweatheropenapi.utils.AIR_CODE
import com.project.newweatheropenapi.utils.DATA_POTAL_SERVICE_KEY
import com.project.newweatheropenapi.utils.DATA_TYPE_LOWER
import com.project.newweatheropenapi.utils.DATE_TERM
import com.project.newweatheropenapi.utils.NUM_OF_ROWS_AIR
import com.project.newweatheropenapi.utils.PAGE_NO_DEFAULT
import com.project.newweatheropenapi.utils.RLTM_DATA_VERSION
import com.project.newweatheropenapi.utils.STATION_VERSION
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(private val repository: AirQualityRepository) :
    BaseViewModel<AirQualityViewState>(AirQualityViewState()) {

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
            repository.getStationFind(request.toMap()).collect { result ->
                _state.value = _state.value.copy(stationFindState = result)

                if (result is ApiResult.Success) {
                    result.value.response.body?.items?.firstOrNull()?.stationName?.let {
                        fetchRltmStation(it)
                    }
                }
            }
        }
    }

    private fun fetchAirQuality(context: Context) {
        val request = AirQualityRequest(
            DATA_POTAL_SERVICE_KEY,
            DATA_TYPE_LOWER,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_AIR,
            TimeManager(context).urlAirQualityDate(),
            AIR_CODE
        )
        fetchData({ repository.getAirQuality(request.toMap()) },
            { currentState, result -> currentState.copy(airQualityState = result) })
    }

    private fun fetchRltmStation(@Query("stationName") stationName: String) {
        val request = RltmStationRequest(
            DATA_POTAL_SERVICE_KEY,
            DATA_TYPE_LOWER,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_AIR,
            stationName,
            DATE_TERM,
            RLTM_DATA_VERSION
        )
        fetchData({ repository.getRltmStation(request.toMap()) },
            { currentState, result -> currentState.copy(rltmStationState = result) })
    }
}