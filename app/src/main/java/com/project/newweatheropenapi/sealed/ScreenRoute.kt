package com.project.newweatheropenapi.sealed

sealed class ScreenRoute(val route: String) {
    abstract val destination: ScreenRoute?

    data object Intro : ScreenRoute("Intro") {
        override val destination: ScreenRoute = Weather
    }
    data object NaverMap : ScreenRoute("NaverMap") {
        override val destination: ScreenRoute = Intro
    }
    data object Weather : ScreenRoute("Weather") {
        override val destination: ScreenRoute = NaverMap
    }
    data object AirQuality : ScreenRoute("AirQuality") {
        override val destination: ScreenRoute = NaverMap
    }
}