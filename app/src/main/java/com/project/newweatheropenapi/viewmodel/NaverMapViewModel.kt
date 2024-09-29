package com.project.newweatheropenapi.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.dataclass.state.NaverMapViewState
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.navermap.NaverMapRequest
import com.project.newweatheropenapi.network.dataclass.request.navermap.toMap
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.network.repository.NaverMapRepository
import com.project.newweatheropenapi.sealed.intent.NaverMapIntent
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.utils.mapAddressConvert
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val repository: NaverMapRepository,
    private val locationDataManager: LocationDataManager,
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

        val request = NaverMapRequest(coords = latLng)
        fetchData({ repository.getReverseGeoCo(request.toMap()) },
            { currentState, result -> currentState.copy(naverMapState = result) })
    }

    private fun reverseGeocode(result: NaverMapResponse) {
        val address = result.mapAddressConvert(context)

        val lastRegion = result.results.last().region.area3.coords.center

        locationDataManager.updateLocationData(
            locationDataManager.locationData.value.latLng,
            address,
            lastRegion.x.toString(),
            lastRegion.y.toString()
        )
    }

    private fun onHandledFlow() {
        viewModelScope.launch {
            state.collect { mapState ->
                state.value.isAllLoading()

                when (mapState.naverMapState) {
                    is ApiResult.Success -> {
                        val response = mapState.naverMapState.value

                        if (response.status.code == 0) {
                            reverseGeocode(response)
                        }
                    }
                    is ApiResult.Error -> logMessage("Error: ${mapState.naverMapState.exception}")
                    else -> {}
                }
            }
        }
    }
}