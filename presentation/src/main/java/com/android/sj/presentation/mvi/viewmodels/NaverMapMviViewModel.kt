package com.android.sj.presentation.mvi.viewmodels

import android.content.Context
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.common.event.UiEvent
import com.android.sj.presentation.common.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.common.state.viewstate.NaverMapViewState
import com.android.sj.presentation.mvi.intent.NaverMapIntent
import com.android.sj.presentation.utils.managers.LoadingStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class NaverMapMviViewModel @Inject constructor(
    private val getReverseGeoCoUseCase: GetReverseGeoCoUseCase,
    private val locationDataManager: LocationDataManager,
    private val mapper : NaverMapPresentationMapper,
    @ApplicationContext val context: Context
) : BaseMviViewModel<NaverMapIntent, NaverMapViewState, Any?>(NaverMapViewState()) {

    init {
        sendIntent(NaverMapIntent.GetLocation)
    }

    override suspend fun handleIntent(intent: NaverMapIntent) {
        when(intent){
            is NaverMapIntent.LoadNaverMapGeo -> fetchNaverMap(intent.lon, intent.lat)
            is NaverMapIntent.GetLocation -> getLocation()
        }
    }

    private fun getLocation() {
        LoadingStateManager.show()
        locationDataManager.getGps { lat, lon ->
            LoadingStateManager.hide()
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
            }
        ){ ui ->
            copy(naverMapUiState = ui)
        }
    }
}