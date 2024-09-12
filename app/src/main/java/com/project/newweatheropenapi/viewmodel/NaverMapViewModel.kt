package com.project.newweatheropenapi.viewmodel

import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.navermap.NaverMapRequest
import com.project.newweatheropenapi.network.dataclass.request.navermap.toMap
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.network.repository.NaverMapRepository
import com.project.newweatheropenapi.utils.DataConverter
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val repository: NaverMapRepository,
    private val locationDataManager: LocationDataManager,
    private val dataConverter: DataConverter,
) : BaseViewModel() {
    private val _naverMapStateFlow = MutableStateFlow<ApiResult<NaverMapResponse>>(ApiResult.Empty)
    val naverMapStateFlow: StateFlow<ApiResult<NaverMapResponse>> = _naverMapStateFlow

    init {
        onHandledFlow()
    }

    fun getLocation() {
//            _locationData.value = LocationData(LatLng(lat=37.51982548626224, lon = 126.88237267230349))
        locationDataManager.getGps { lat, lon ->
            fetchNaverMap(lon, lat)
        }
    }

    fun fetchNaverMap(lon : Double, lat:Double) {
        val latLng = "$lon,$lat"
        locationDataManager.updateLocationData(LatLng(lat,lon),"")

        val request = NaverMapRequest(coords = latLng)
        fetchData({ repository.getReverseGeoCo(request.toMap()) }, _naverMapStateFlow)
    }

    fun reverseGeocode(result: NaverMapResponse) {
        val address = dataConverter.mapAddressConvert(result)
        locationDataManager.updateLocationData(locationDataManager.locationData.value.latLng, address)
    }

    private fun onHandledFlow() {
        viewModelScope.launch {
            naverMapStateFlow.collect { naverMapState ->
                when (naverMapState) {
                    is ApiResult.Success -> {
                        val response = (_naverMapStateFlow.value as ApiResult.Success).value
                        if(response.status.code==0) {
                            reverseGeocode(response)
                        }
                    }
                    is ApiResult.Empty -> {}
                    is ApiResult.Error -> logMessage("Error: ${naverMapState.exception}")
                    is ApiResult.Loading -> {}
                }
            }
        }
    }
}