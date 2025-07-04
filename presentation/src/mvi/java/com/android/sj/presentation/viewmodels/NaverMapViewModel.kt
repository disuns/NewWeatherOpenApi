package com.android.sj.presentation.viewmodels

import android.content.Context
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.common.event.UiEvent
import com.android.sj.presentation.common.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.common.state.viewstate.NaverMapViewState
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.partialstate.NaverMapPartialState
import com.android.sj.presentation.utils.managers.LoadingStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val getReverseGeoCoUseCase: GetReverseGeoCoUseCase,
    private val locationDataManager: LocationDataManager,
    private val mapper : NaverMapPresentationMapper,
    @ApplicationContext val context: Context
) : BaseViewModel<NaverMapIntent, NaverMapViewState, NaverMapPartialState>(NaverMapViewState()) {
    override suspend fun handleIntent(intent: NaverMapIntent) {
        when(intent){
            is NaverMapIntent.LoadNaverMapGeo -> fetchNaverMap(intent.lon, intent.lat)
            is NaverMapIntent.GetLocation -> getLocation()
        }
    }

    override fun reduceState(
        current: NaverMapViewState,
        partial: NaverMapPartialState
    ): NaverMapViewState {
        return when(partial){
            is NaverMapPartialState.LoadingNaverMap ->
                current.copy(naverMapUiState =  BaseUiState(isLoading = true))
            is NaverMapPartialState.NaverMapSuccess ->
                current.copy(naverMapUiState = BaseUiState(model = partial.data))
            is NaverMapPartialState.NaverMapEmpty ->
                current.copy(
                    naverMapUiState = BaseUiState(
                        isEmptyData = true,
                        errorMessage = partial.message
                    )
                )
            is NaverMapPartialState.NaverMapError ->
                current.copy(
                    naverMapUiState = BaseUiState(
                        isError = true,
                        errorMessage = partial.message,
                        errorCode = partial.code
                    )
                )
        }
    }

    private fun getLocation() {
        LoadingStateManager.show()
        locationDataManager.getGps(onStopGps = {LoadingStateManager.hide()}) { lat, lon ->
            sendIntent(NaverMapIntent.LoadNaverMapGeo(lon, lat))
        }
    }

    private fun fetchNaverMap(lon: Double, lat: Double) {
        sendEffect(UiEvent.UpdateLocation(lat = lat, lon = lon))
        fetchAndReduce(
            usecase = getReverseGeoCoUseCase("$lon,$lat"),
            mapper = mapper::domainToUIReverseGeoCo,
            onFetchSuccessAfter = { result ->
                with(locationDataManager.locationData.value) {
                    sendEffect(
                        UiEvent.UpdateLocation(
                            lat = lat,
                            lon = lng,
                            address = result.mapAddress,
                            x = result.centerX,
                            y =  result.centerY)
                    )
                }
            },
            emitEmpty = NaverMapPartialState.NaverMapEmpty("Empty Data"),
            emitLoading = NaverMapPartialState.LoadingNaverMap,
            emitError = { msg, code -> NaverMapPartialState.NaverMapError(msg, code) },
            emitSuccess = { model -> NaverMapPartialState.NaverMapSuccess(model) }
        )
    }
}