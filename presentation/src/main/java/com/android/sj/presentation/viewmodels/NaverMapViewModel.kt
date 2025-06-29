package com.android.sj.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.common.utils.logMessage
import com.android.sj.domain.ApiResult
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.NaverMapPresentationMapperFactory
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.models.state.uistate.ReverseGeoUIState
import com.android.sj.presentation.models.state.viewstate.AirQualityViewState
import com.android.sj.presentation.models.state.viewstate.NaverMapViewState
import com.android.sj.presentation.utils.managers.LoadingStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val getReverseGeoCoUseCase: GetReverseGeoCoUseCase,
    private val locationDataManager: LocationDataManager,
    mapperFactory: NaverMapPresentationMapperFactory,
    @ApplicationContext val context: Context
) : BaseViewModel<NaverMapViewState>(NaverMapViewState()) {
    private val mapper = mapperFactory.create(viewModelScope)

    private val _reverseGeoState = MutableStateFlow(ReverseGeoUIState())

    val viewState = combine(
        _reverseGeoState
    ){ regeo ->
        NaverMapViewState(regeo)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, NaverMapViewState())

    init {
        onHandledFlow()
    }

    fun handleIntent(intent: NaverMapIntent) {
        when(intent){
            is NaverMapIntent.LoadNaverMapGeo -> fetchNaverMap(intent.lon, intent.lat)
            is NaverMapIntent.GetLocation -> getLocation()
        }
    }

    private fun getLocation() {
        LoadingStateManager.isShow(true)
        locationDataManager.getGps { lat, lon ->
            fetchNaverMap(lon, lat)
        }
    }

    private fun fetchNaverMap(lon: Double, lat: Double) {
        locationDataManager.updateLocationData(lat = lat, lon = lon)

        fetchData(mapper.domainToUIReverseGeoCo(getReverseGeoCoUseCase("$lon,$lat"))) { currentState, result->
            currentState.copy(naverMapState = result)
        }
    }

    private fun onHandledFlow() {
        viewModelScope.launch {
            state.collect { mapState ->
                mapState.isAllLoading()

                when (mapState.naverMapState) {
                    is ApiResult.Success -> {
                        val data = mapState.naverMapState.value
                        locationDataManager.updateLocationData(
                            locationDataManager.locationData.value.lat,
                            locationDataManager.locationData.value.lng,
                            data.mapAddress,
                            data.centerX,
                            data.centerY
                        )
                    }
                    is ApiResult.Error -> logMessage("Error: ${mapState.naverMapState.exception}")
                    else -> {}
                }
            }
        }
    }
}