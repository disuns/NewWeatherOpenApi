package com.project.newweatheropenapi.viewmodel

import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.common.DataConverter
import com.project.newweatheropenapi.common.logMessage
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.navermap.NaverMapRequest
import com.project.newweatheropenapi.network.dataclass.request.navermap.toMap
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.network.repository.NaverMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    private val repository: NaverMapRepository,
    private val dataConverter: DataConverter,
    private val activityViewModel: ActivityViewModel
) : BaseViewModel() {

    private val _naverMapStateFlow =
        MutableStateFlow<ApiResult<NaverMapResponse>>(ApiResult.Loading)
    val naverMapStateFlow: StateFlow<ApiResult<NaverMapResponse>> = _naverMapStateFlow

    init {
        observeNaverMapState()
    }

    fun fetchNaverMap(@Query("coords", encoded = true) coords: String) {
        val request = NaverMapRequest(coords = coords)
        fetchData({ repository.getReverseGeoCo(request.toMap()) }, _naverMapStateFlow)
    }

    private fun observeNaverMapState() {
        viewModelScope.launch {
            naverMapStateFlow.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val address = dataConverter.mapAddressConvert(result.value)
                        activityViewModel.updateLocationData(LatLng(result.value.results[0].region.area0.coords.center.x, result.value.results[0].region.area0.coords.center.y) , address)
                    }

                    is ApiResult.Error -> logMessage("error code : ${result.code}   exception : ${result.exception}")

                    is ApiResult.Loading -> {}

                    ApiResult.Empty -> logMessage("data empty")
                }
            }
        }
    }


}