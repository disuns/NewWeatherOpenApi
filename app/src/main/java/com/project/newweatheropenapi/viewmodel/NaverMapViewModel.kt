package com.project.newweatheropenapi.viewmodel

import com.project.newweatheropenapi.common.DATA_TYPE_LOWER
import com.project.newweatheropenapi.common.MAP_CLIENT_KEY
import com.project.newweatheropenapi.common.MAP_CLIENT_KEY_ID
import com.project.newweatheropenapi.common.MAP_COORDINATE_DEFAULT
import com.project.newweatheropenapi.common.MAP_COORDINATE_TM
import com.project.newweatheropenapi.common.MAP_ORDERS
import com.project.newweatheropenapi.common.MAP_REQUEST_DEFAULT
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.navermap.NaverMapRequest
import com.project.newweatheropenapi.network.dataclass.request.navermap.toMap
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.network.repository.NaverMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(private val repository: NaverMapRepository) :
    BaseViewModel() {
    private val _naverMapStateFlow = MutableStateFlow<ApiResult<NaverMapResponse>>(ApiResult.Loading)
    val naverMapStateFlow: StateFlow<ApiResult<NaverMapResponse>> = _naverMapStateFlow

    fun fetchNaverMap(@Query("coords", encoded = true) coords: String) {
        val request = NaverMapRequest(
            MAP_REQUEST_DEFAULT,
            coords,
            MAP_COORDINATE_DEFAULT,
            MAP_COORDINATE_TM,
            DATA_TYPE_LOWER,
            MAP_ORDERS,
            MAP_CLIENT_KEY_ID,
            MAP_CLIENT_KEY
        )
        fetchData({ repository.getReverseGeoCo(request.toMap()) }, _naverMapStateFlow)
    }
}