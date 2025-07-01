package com.android.sj.presentation.mvvm.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.common.utils.logMessage
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.event.UiEvent
import com.android.sj.presentation.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.models.state.viewstate.NaverMapViewState
import com.android.sj.presentation.utils.managers.LoadingStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val getReverseGeoCoUseCase: GetReverseGeoCoUseCase,
    private val locationDataManager: LocationDataManager,
    private val mapper : NaverMapPresentationMapper,
    @ApplicationContext val context: Context
) : BaseViewModel<NaverMapViewState>(NaverMapViewState()) {

    init {
        onHandledFlow()
    }

//    fun handleIntent(intent: NaverMapIntent) {
//        when(intent){
//            is NaverMapIntent.LoadNaverMapGeo -> fetchNaverMap(intent.lon, intent.lat)
//            is NaverMapIntent.GetLocation -> getLocation()
//        }
//    }

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
                            sendEvent(UiEvent.UpdateLocation(
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