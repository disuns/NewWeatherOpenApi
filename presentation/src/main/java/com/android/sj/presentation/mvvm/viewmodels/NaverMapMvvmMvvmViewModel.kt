package com.android.sj.presentation.mvvm.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.common.utils.logMessage
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.common.event.UiEvent
import com.android.sj.presentation.common.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.common.state.viewstate.NaverMapViewState
import com.android.sj.presentation.utils.managers.LoadingStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapMvvmMvvmViewModel @Inject constructor(
    private val getReverseGeoCoUseCase: GetReverseGeoCoUseCase,
    private val locationDataManager: LocationDataManager,
    private val mapper : NaverMapPresentationMapper,
    @ApplicationContext val context: Context
) : BaseMvvmViewModel<NaverMapViewState>(NaverMapViewState()) {

    init {
        onHandledFlow()
    }

    fun getLocation() {
        LoadingStateManager.isShow(true)
        locationDataManager.getGps { lat, lon ->
            fetchNaverMap(lon, lat)
        }
    }

    fun fetchNaverMap(lon: Double, lat: Double) {
        sendEvent(UiEvent.UpdateLocation(lat = lat, lon = lon))
        fetchData(
            usecase = getReverseGeoCoUseCase("$lon,$lat"),
            mapper = mapper::domainToUIReverseGeoCo
        ){ ui ->
            copy(naverMapUiState = ui)
        }
    }

    private fun onHandledFlow() {
        viewModelScope.launch {
            viewState.collect { mapState ->
                mapState.isAllLoading()

                val uiState = mapState.naverMapUiState
                when {
                    uiState.isError -> {
                        logMessage("Error: ${uiState.errorMessage}")
                    }
                    uiState.isEmptyData || uiState.isLoading -> {
                    }
                    uiState.model != null -> {
                        with(locationDataManager.locationData.value) {
                            sendEvent(
                                UiEvent.UpdateLocation(
                                lat = lat,
                                lon = lng,
                                address = uiState.model.mapAddress,
                                x = uiState.model.centerX,
                                y =  uiState.model.centerY)
                            )
                        }
                    }
                }
            }
        }
    }
}