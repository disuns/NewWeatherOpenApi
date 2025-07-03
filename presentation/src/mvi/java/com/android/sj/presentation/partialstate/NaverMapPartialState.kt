package com.android.sj.presentation.mvi.partialstate

import com.android.sj.presentation.common.models.uimodels.navermap.ReverseGeoUIModel

sealed class NaverMapPartialState {
    object LoadingNaverMap : NaverMapPartialState()
    data class NaverMapSuccess(val data: ReverseGeoUIModel) : NaverMapPartialState()
    data class NaverMapError(val message: String, val code: Int? = null) : NaverMapPartialState()
    data class NaverMapEmpty(val message: String) : NaverMapPartialState()
}