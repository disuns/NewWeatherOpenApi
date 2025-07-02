package com.android.sj.presentation.mvi.viewmodels

import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase
import com.android.sj.presentation.common.mappers.AirQualityPresentationMapper
import com.android.sj.presentation.common.state.uistate.RltmStationUiState
import com.android.sj.presentation.common.state.viewstate.AirQualityViewState
import com.android.sj.presentation.mvi.intent.AirQualityIntent
import com.android.sj.presentation.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AirQualityMviMvvmViewModel @Inject constructor(
    private val getAirQualityUseCase : GetAirQualityUseCase,
    private val getRltmStationUseCase : GetRltmStationUseCase,
    private val getStationFindUseCase : GetStationFindUseCase,
    private val timeManager: TimeManager,
    private val mapper : AirQualityPresentationMapper
) : BaseMviViewModel<AirQualityIntent, AirQualityViewState>(AirQualityViewState()) {

    override suspend fun handleIntent(intent: AirQualityIntent) {
        when (intent) {
            is AirQualityIntent.LoadAllAirQuality ->  fetchAllAirQualityData(intent.regionX, intent.regionY)
            is AirQualityIntent.LoadAirQuality -> fetchAirQuality()
            is AirQualityIntent.LoadRltmStation -> fetchRltmStation(intent.stationName)
            is AirQualityIntent.LoadStationFind -> fetchStationFindAndThenRltmStation(intent.regionX, intent.regionY)
        }
    }

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
                if(data != null && data != "정보없음") fetchRltmStation(data)
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