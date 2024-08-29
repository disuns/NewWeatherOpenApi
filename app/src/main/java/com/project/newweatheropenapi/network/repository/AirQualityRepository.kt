package com.project.newweatheropenapi.network.repository

import com.project.newweatheropenapi.network.safeFlow
import com.project.newweatheropenapi.network.service.AirQualityService
import retrofit2.http.QueryMap
import javax.inject.Inject

class AirQualityRepository @Inject constructor(private val service: AirQualityService){

    fun getAirQuality(@QueryMap params: Map<String, String>) = safeFlow { service.getAirQuality(params) }
    fun getRltmStation(@QueryMap params: Map<String, String>) = safeFlow { service.getRltmStation(params) }
    fun getStationFind(@QueryMap params: Map<String, String>) = safeFlow { service.getStationFind(params) }
}