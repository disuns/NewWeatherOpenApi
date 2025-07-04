package com.android.sj.presentation.viewmodels

import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase
import com.android.sj.presentation.common.mappers.AirQualityPresentationMapper
import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.common.state.viewstate.AirQualityViewState
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.partialstate.AirQualityPartialState
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
) : BaseViewModel<AirQualityIntent, AirQualityViewState, AirQualityPartialState>(AirQualityViewState()) {

    override suspend fun handleIntent(intent: AirQualityIntent) {
        when (intent) {
            is AirQualityIntent.LoadAllAirQuality ->  {
                sendIntent(AirQualityIntent.LoadAirQuality)
                sendIntent(AirQualityIntent.LoadStationFind(intent.regionX, intent.regionY))
            }
            is AirQualityIntent.LoadAirQuality -> fetchAirQuality()
            is AirQualityIntent.LoadRltmStation -> fetchRltmStation(intent.stationName)
            is AirQualityIntent.LoadStationFind -> fetchStationFindAndThenRltmStation(intent.regionX, intent.regionY)
        }
    }

    override fun reduceState(
        current: AirQualityViewState,
        partial: AirQualityPartialState
    ): AirQualityViewState {
        return when(partial){
            is AirQualityPartialState.LoadingAirQuality ->
                current.copy(airQualityUiState = BaseUiState(isLoading = true))
            is AirQualityPartialState.AirQualitySuccess ->
                current.copy(airQualityUiState = BaseUiState(model = partial.data))
            is AirQualityPartialState.AirQualityError ->
                current.copy(
                    airQualityUiState = BaseUiState(
                        isError = true,
                        errorMessage = partial.message,
                        errorCode = partial.code
                    )
                )
            is AirQualityPartialState.AirQualityEmpty -> current.copy(
                airQualityUiState = BaseUiState(
                    isEmptyData = true,
                    errorMessage = partial.message
                )
            )
            is AirQualityPartialState.LoadingRltmStation ->
                current.copy(rltmStationUiState = BaseUiState(isLoading = true))
            is AirQualityPartialState.RltmStationSuccess ->
                current.copy(rltmStationUiState = BaseUiState(model = partial.data))
            is AirQualityPartialState.RltmStationError ->
                current.copy(
                    rltmStationUiState = BaseUiState(
                        isError = true,
                        errorMessage = partial.message,
                        errorCode = partial.code
                    )
                )
            is AirQualityPartialState.RltmStationEmpty -> current.copy(
                rltmStationUiState = BaseUiState(
                    isEmptyData = true,
                    errorMessage = partial.message
                )
            )
            is AirQualityPartialState.LoadingStationFind ->
                current.copy(stationFindUiState = BaseUiState(isLoading = true))
            is AirQualityPartialState.StationFindSuccess ->
                current.copy(stationFindUiState = BaseUiState(model = partial.data))
            is AirQualityPartialState.StationFindError ->
                current.copy(
                    stationFindUiState = BaseUiState(
                        isError = true,
                        errorMessage = partial.message,
                        errorCode = partial.code
                    )
                )
            is AirQualityPartialState.StationFindEmpty -> current.copy(
                stationFindUiState = BaseUiState(
                    isEmptyData = true,
                    errorMessage = partial.message
                )
            )
        }
    }

    private fun fetchStationFindAndThenRltmStation(regionX: String, regionY: String) {
        fetchAndReduce(
            usecase = getStationFindUseCase(regionX, regionY),
            mapper = mapper::domainToUIStationFind,
            onFetchSuccessBefore = { result ->
                val data = result.stationName
                if(!data.isNullOrBlank() && data != "정보없음") sendIntent(AirQualityIntent.LoadRltmStation(data))
            },
            emitEmpty = AirQualityPartialState.StationFindEmpty("Empty Data"),
            emitLoading = AirQualityPartialState.LoadingStationFind,
            emitError = { msg, code ->AirQualityPartialState.StationFindError(msg, code) },
            emitSuccess = { model -> AirQualityPartialState.StationFindSuccess(model) }
        )
    }

    private fun fetchAirQuality() {
        fetchAndReduce(
            usecase = getAirQualityUseCase(timeManager.urlAirQualityDate()),
            mapper = mapper::domainToUIAirQuality,
            emitEmpty = AirQualityPartialState.AirQualityEmpty("Empty Data"),
            emitLoading = AirQualityPartialState.LoadingAirQuality,
            emitError = { msg, code ->AirQualityPartialState.AirQualityError(msg, code) },
            emitSuccess = { model -> AirQualityPartialState.AirQualitySuccess(model) }
        )
    }

    private fun fetchRltmStation(stationName: String) {
        fetchAndReduce(
            usecase = getRltmStationUseCase(stationName),
            mapper = mapper::domainToUIRltmStation,
            emitEmpty = AirQualityPartialState.RltmStationEmpty("Empty Data"),
            emitLoading = AirQualityPartialState.LoadingRltmStation,
            emitError = { msg, code ->AirQualityPartialState.RltmStationError(msg, code) },
            emitSuccess = { model -> AirQualityPartialState.RltmStationSuccess(model) }
        )
    }
}