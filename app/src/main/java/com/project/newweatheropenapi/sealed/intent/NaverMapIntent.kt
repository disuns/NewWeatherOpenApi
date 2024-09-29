package com.project.newweatheropenapi.sealed.intent

sealed class NaverMapIntent {
    data class LoadNaverMapGeo(val lon: Double, val lat: Double) : NaverMapIntent()
}