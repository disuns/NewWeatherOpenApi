package com.android.sj.presentation.mvi.intent

sealed class AirQualityIntent {
    data class LoadAllAirQuality(val regionX: String, val regionY: String) : AirQualityIntent()
    object LoadAirQuality : AirQualityIntent()
    data class LoadRltmStation(val stationName: String) : AirQualityIntent()
    data class LoadStationFind(val regionX: String, val regionY: String) : AirQualityIntent()
}