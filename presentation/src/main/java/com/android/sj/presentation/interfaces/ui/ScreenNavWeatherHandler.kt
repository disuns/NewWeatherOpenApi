package com.android.sj.presentation.interfaces.ui

interface ScreenNavWeatherHandler {
    fun fetchAll(nx: String, ny: String, address: String)
    fun onNowError(lat: String, lon: String)
    fun onTimeError(lat: String, lon: String)
    fun onWeekError(address: String)
}