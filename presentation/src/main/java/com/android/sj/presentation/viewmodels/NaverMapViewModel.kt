package com.android.sj.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.common.utils.logMessage
import com.android.sj.domain.ApiResult
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.models.state.NaverMapViewState
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

    fun handleIntent(intent: NaverMapIntent) {
        super.handleIntent(intent)
        when(intent){
            is NaverMapIntent.LoadNaverMapGeo -> fetchNaverMap(intent.lon, intent.lat)
            is NaverMapIntent.GetLocation -> getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        LoadingStateManager.isShow(true)
        locationDataManager.getGps { lat, lon ->
            fetchNaverMap(lon, lat)
        }
    }

    private fun fetchNaverMap(lon: Double, lat: Double) {
        val latLng = "$lon,$lat"
        locationDataManager.updateLocationData(lat = lat, lon = lon)

        fetchData(mapper.domainToUIReverseGeoCo(getReverseGeoCoUseCase(latLng))) { currentState, result->
            currentState.copy(naverMapState = result)
        }
    }

    private fun onHandledFlow() {
        viewModelScope.launch {
            state.collect { mapState ->
                state.value.isAllLoading()

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