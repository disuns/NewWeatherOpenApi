package com.project.newweatheropenapi.sealed.intent

import android.content.Context

sealed class AirQualityIntent {
    data class LoadAllAirQuality(val regionX: String, val regionY: String, val context: Context) : AirQualityIntent()
    data class LoadAirQuality(val context: Context) : AirQualityIntent()
    data class LoadStationFind(val regionX: String, val regionY: String) : AirQualityIntent()
    data class LoadRltmStation(val stationName: String) : AirQualityIntent()
}