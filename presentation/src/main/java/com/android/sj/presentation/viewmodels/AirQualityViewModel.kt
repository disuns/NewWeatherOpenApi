package com.android.sj.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase
import com.android.sj.presentation.AirQualityPresentationMapperFactory
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.models.state.uistate.AirQualityUiState
import com.android.sj.presentation.models.state.uistate.RltmStationUiState
import com.android.sj.presentation.models.state.uistate.StationFindUiState
import com.android.sj.presentation.models.state.viewstate.AirQualityViewState
import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val getAirQualityUseCase : GetAirQualityUseCase,
    private val getRltmStationUseCase : GetRltmStationUseCase,
    private val getStationFindUseCase : GetStationFindUseCase,
    private val timeManager: TimeManager,
    mapperFactory: AirQualityPresentationMapperFactory
) : BaseViewModel<AirQualityViewState>(AirQualityViewState()) {
    private val _airQualityState = MutableStateFlow(AirQualityUiState())
    private val _rltmStationState = MutableStateFlow(RltmStationUiState())
    private val _stationFindState = MutableStateFlow(StationFindUiState())

    val viewState = combine(
        _airQualityState, _rltmStationState, _stationFindState
    ){ air, rltm, station ->
        AirQualityViewState(air, rltm, station)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, AirQualityViewState())

    private val mapper = mapperFactory.create(viewModelScope)

    fun handleIntent(intent: AirQualityIntent) {
        super.handleIntent(intent)
        when (intent) {
            is AirQualityIntent.LoadAllAirQuality -> fetchAllAirQualityData(intent.regionX, intent.regionY, intent.context)
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
            _viewState.update {
                it.airQualityUiState
            }
            getStationFindUseCase(regionX, regionY)
                .onStart {
                    _stationFindState.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = null,
                            isError = false
                        )
                    }
                }
                .collect { result ->
                    _stationFindState.update {
                        when (result) {
                            is ApiResult.Success -> {
                                val data = result.value.stationName
                                if(data != null && data != "정보없음") fetchRltmStation(data)
                                it.copy(
                                    model = mapper.domainToUIStationFind(result.value),
                                    isLoading = false,
                                    isError = false
                                )
                            }
                            is ApiResult.Error -> it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.exception?.message ?: "Unknown Error"
                            )
                            is ApiResult.Empty -> it.copy(isLoading = false, isError = false)
                        }
                    }
                }
        }
    }

    private fun fetchAirQuality() {
        fetchData(
            usecase = getAirQualityUseCase(timeManager.urlAirQualityDate()),
            mapper = mapper::domainToUIAirQuality,
            initialData = AirQualityUiModel.EMPTY
        ){ ui->
            copy(airQualityUiState = ui)
        }
    }

    private fun fetchRltmStation(stationName: String) {
        fetchData(
            usecase = getRltmStationUseCase(stationName),
            mapper = mapper::domainToUIRltmStation,
            initialData = RltmStationUiState()
        ){ ui ->
            copy(rltmStationUiState = ui)
        }
        viewModelScope.launch {
            getRltmStationUseCase(stationName)
                .onStart {
                    _rltmStationState.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = null,
                            isError = false
                        )
                    }
                }
                .collect{ result->
                    _rltmStationState.update {
                        when(result){
                            is ApiResult.Success -> it.copy(
                                model = mapper.domainToUIRltmStation(result.value),
                                isLoading = false,
                                isError = false
                            )
                            is ApiResult.Error -> it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.exception?.message ?: "Unknown Error"
                            )
                            is ApiResult.Empty-> it.copy(isLoading = false, isError = false)
                        }
                    }
                }
        }
    }
}