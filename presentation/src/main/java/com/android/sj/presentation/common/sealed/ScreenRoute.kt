package com.android.sj.presentation.common.sealed

sealed class ScreenRoute(val route: String, val showBottom : Boolean) {
    abstract val destination: ScreenRoute?

    data object Intro : ScreenRoute("Intro", false) {
        override val destination: ScreenRoute = Weather
    }
    data object NaverMap : ScreenRoute("NaverMap", false) {
        override val destination: ScreenRoute = Intro
    }
    data object Weather : ScreenRoute("Weather", true) {
        override val destination: ScreenRoute = NaverMap
    }
    data object AirQuality : ScreenRoute("AirQuality", true) {
        override val destination: ScreenRoute = NaverMap
    }

    companion object {
        val allRoutes = listOf(Intro, NaverMap, Weather, AirQuality)
    }
}