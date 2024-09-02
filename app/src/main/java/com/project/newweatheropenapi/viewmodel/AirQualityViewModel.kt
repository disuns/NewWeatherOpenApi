package com.project.newweatheropenapi.viewmodel

import com.project.newweatheropenapi.common.AIR_CODE
import com.project.newweatheropenapi.common.DATA_POTAL_SERVICE_KEY
import com.project.newweatheropenapi.common.DATA_TYPE_LOWER
import com.project.newweatheropenapi.common.DATE_TERM
import com.project.newweatheropenapi.common.NUM_OF_ROWS_AIR
import com.project.newweatheropenapi.common.PAGE_NO_DEFAULT
import com.project.newweatheropenapi.common.RLTM_DATA_VERSION
import com.project.newweatheropenapi.common.STATION_VERSION
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.datapotal.AirQualityRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.RltmStationRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.StationFindRequest
import com.project.newweatheropenapi.network.dataclass.request.datapotal.toMap
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.RltmStationResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.StationFindResponse
import com.project.newweatheropenapi.network.repository.AirQualityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(private val repository: AirQualityRepository) :
    BaseViewModel() {
    private val _airQualityStateFlow = MutableStateFlow<ApiResult<AirQualityResponse>>(ApiResult.Loading)
    val airQualityStateFlow: StateFlow<ApiResult<AirQualityResponse>> = _airQualityStateFlow

    private val _rltmStationStateFlow = MutableStateFlow<ApiResult<RltmStationResponse>>(ApiResult.Loading)
    val rltmStationStateFlow: StateFlow<ApiResult<RltmStationResponse>> = _rltmStationStateFlow

    private val _stationFindStateFlow = MutableStateFlow<ApiResult<StationFindResponse>>(ApiResult.Loading)
    val stationFindStateFlow: StateFlow<ApiResult<StationFindResponse>> = _stationFindStateFlow

    fun fetchAirQuality(@Query("searchDate") searchDate: String) {
        val request = AirQualityRequest(
            DATA_POTAL_SERVICE_KEY,
            DATA_TYPE_LOWER,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_AIR,
            searchDate,
            AIR_CODE
        )
        fetchData({ repository.getAirQuality(request.toMap()) }, _airQualityStateFlow)
    }

    fun fetchRltmStation(@Query("stationName") stationName: String) {
        val request = RltmStationRequest(
            DATA_POTAL_SERVICE_KEY,
            DATA_TYPE_LOWER,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_AIR,
            stationName,
            DATE_TERM,
            RLTM_DATA_VERSION
        )
        fetchData({ repository.getRltmStation(request.toMap()) }, _rltmStationStateFlow)
    }

    fun fetchStationFind(
        @Query("tmX") tmX: String,
        @Query("tmY") tmY: String
    ) {
        val request = StationFindRequest(
            DATA_POTAL_SERVICE_KEY, DATA_TYPE_LOWER, tmX, tmY, STATION_VERSION
        )
        fetchData({ repository.getStationFind(request.toMap()) }, _stationFindStateFlow)
    }
}