package com.android.sj.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.common.utils.logMessage
import com.android.sj.common.utils.managers.LocationDataManager
import com.android.sj.domain.ApiResult
import com.android.sj.domain.usecase.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.mappers.PresentationMapper
import com.android.sj.presentation.models.request.navermap.NaverMapRequest
import com.android.sj.presentation.models.request.navermap.toMap
import com.android.sj.presentation.state.NaverMapViewState
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val getReverseGeoCoUseCase: GetReverseGeoCoUseCase,
    private val locationDataManager: LocationDataManager,
    private val mapper : PresentationMapper,
    @ApplicationContext val context: Context
) : BaseViewModel<NaverMapViewState>(NaverMapViewState()) {
    init {
        onHandledFlow()
    }

    fun handleIntent(intent: NaverMapIntent) {
        super.handleIntent(intent)
        when(intent){
            is NaverMapIntent.LoadNaverMapGeo -> fetchNaverMap(intent.lon, intent.lat)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        locationDataManager.getGps { lat, lon ->
            fetchNaverMap(lon, lat)
        }
    }

    private fun fetchNaverMap(lon: Double, lat: Double) {
        val latLng = "$lon,$lat"
        locationDataManager.updateLocationData(LatLng(lat, lon))

        viewModelScope.launch {
            val request = NaverMapRequest(coords = latLng)

            mapper.domainToUIReverseGeoCo(getReverseGeoCoUseCase(request.toMap())).collect { result ->
                _state.value = _state.value.copy(naverMapState = result)
            }
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
                            locationDataManager.locationData.value.latLng,
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