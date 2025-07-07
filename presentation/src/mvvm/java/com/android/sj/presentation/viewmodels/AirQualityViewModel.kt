package com.android.sj.presentation.viewmodels

import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase
import com.android.sj.presentation.mappers.AirQualityPresentationMapper
import com.android.sj.presentation.state.uistate.RltmStationUiState
import com.android.sj.presentation.state.viewstate.AirQualityViewState
import com.android.sj.presentation.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val getAirQualityUseCase : GetAirQualityUseCase,
    private val getRltmStationUseCase : GetRltmStationUseCase,
    private val getStationFindUseCase : GetStationFindUseCase,
    private val timeManager: TimeManager,
    private val mapper : AirQualityPresentationMapper
) : BaseViewModel<AirQualityViewState>(AirQualityViewState()) {

    fun fetchAllAirQualityData(
        regionX: String,
        regionY: String
    ) {
        fetchAllData(
            { fetchAirQuality() },
            { fetchStationFindAndThenRltmStation(regionX, regionY) }
        )
    }

    fun fetchStationFindAndThenRltmStation(regionX: String, regionY: String) {
        fetchData(
            usecase = getStationFindUseCase(regionX, regionY),
            mapper = mapper::domainToUIStationFind,
            onFetchSuccessBefore = { result ->
                val data = result.stationName
                if(!data.isNullOrBlank() && data != "정보없음") fetchRltmStation(data)
            }
        ) { ui ->
            copy(
                stationFindUiState = ui,
                rltmStationUiState = RltmStationUiState()
            )
        }
    }

    fun fetchAirQuality() {
        fetchData(
            usecase = getAirQualityUseCase(timeManager.urlAirQualityDate()),
            mapper = mapper::domainToUIAirQuality
        ){ ui->
            copy(airQualityUiState = ui)
        }
    }

    fun fetchRltmStation(stationName: String) {
        fetchData(
            usecase = getRltmStationUseCase(stationName),
            mapper = mapper::domainToUIRltmStation
        ){ ui ->
            copy(rltmStationUiState = ui)
        }
    }
}