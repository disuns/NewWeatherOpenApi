package com.android.sj.presentation.sealed

sealed class ScreenRoute(val route: String, val showBottom : Boolean) {
    abstract val destination: com.android.sj.presentation.sealed.ScreenRoute?

    data object Intro : com.android.sj.presentation.sealed.ScreenRoute("Intro", false) {
        override val destination: com.android.sj.presentation.sealed.ScreenRoute =
            com.android.sj.presentation.sealed.ScreenRoute.Weather
    }
    data object NaverMap : com.android.sj.presentation.sealed.ScreenRoute("NaverMap", false) {
        override val destination: com.android.sj.presentation.sealed.ScreenRoute =
            com.android.sj.presentation.sealed.ScreenRoute.Intro
    }
    data object Weather : com.android.sj.presentation.sealed.ScreenRoute("Weather", true) {
        override val destination: com.android.sj.presentation.sealed.ScreenRoute =
            com.android.sj.presentation.sealed.ScreenRoute.NaverMap
    }
    data object AirQuality : com.android.sj.presentation.sealed.ScreenRoute("AirQuality", true) {
        override val destination: com.android.sj.presentation.sealed.ScreenRoute =
            com.android.sj.presentation.sealed.ScreenRoute.NaverMap
    }

    companion object {
        val allRoutes = listOf(
            com.android.sj.presentation.sealed.ScreenRoute.Intro,
            com.android.sj.presentation.sealed.ScreenRoute.NaverMap,
            com.android.sj.presentation.sealed.ScreenRoute.Weather,
            com.android.sj.presentation.sealed.ScreenRoute.AirQuality
        )
    }
}