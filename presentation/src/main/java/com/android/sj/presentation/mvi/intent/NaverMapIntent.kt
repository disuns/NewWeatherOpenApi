package com.android.sj.presentation.mvi.intent

sealed class NaverMapIntent {
    data class LoadNaverMapGeo(val lon: Double, val lat: Double) : NaverMapIntent()
    object GetLocation:NaverMapIntent()
}