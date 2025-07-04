package com.android.sj.presentation.event

import com.android.sj.presentation.sealed.ScreenRoute

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class UpdateLocation(
        val address: String = "",
        val lat: Double,
        val lon: Double,
        val x: String = "",
        val y: String = ""
    ) : UiEvent()
    data class Navigate(val route : ScreenRoute) : UiEvent()
}