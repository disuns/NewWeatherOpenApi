package com.android.sj.presentation.viewmodels

import android.content.Context
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.event.UiEvent
import com.android.sj.presentation.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.state.viewstate.NaverMapViewState
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
) : BaseViewModel<NaverMapViewState>(NaverMapViewState()) {
    fun getLocation() {
        LoadingStateManager.show()
        locationDataManager.getGps(onStopGps = {LoadingStateManager.hide()}) { lat, lon ->
            fetchNaverMap(lon, lat)
        }
    }

    fun fetchNaverMap(lon: Double, lat: Double) {
        sendEvent(UiEvent.UpdateLocation(lat = lat, lon = lon))
        fetchData(
            usecase = getReverseGeoCoUseCase("$lon,$lat"),
            mapper = mapper::domainToUIReverseGeoCo,
            onFetchSuccessAfter = { result ->
                with(locationDataManager.locationData.value) {
                    sendEvent(
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