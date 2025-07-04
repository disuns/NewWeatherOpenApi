package com.android.sj.presentation.interfaces.ui

interface ScreenNavAirQualityHandler {
    fun fetchAll(x: String, y: String)
    fun onStationError(x: String, y: String)
    fun onAirQualityError()
}