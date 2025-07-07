package com.android.sj.presentation.intent

sealed class NaverMapIntent {
    data class LoadNaverMapGeo(val lon: Double, val lat: Double) : NaverMapIntent()
    object GetLocation:NaverMapIntent()
}