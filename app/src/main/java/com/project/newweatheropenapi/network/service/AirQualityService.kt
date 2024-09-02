package com.project.newweatheropenapi.network.service

import com.project.newweatheropenapi.common.AIR_QUALITY_FRCST
import com.project.newweatheropenapi.common.RLTM_STATION
import com.project.newweatheropenapi.common.STATION_FIND
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.RltmStationResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.StationFindResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AirQualityService {
    @GET(AIR_QUALITY_FRCST)
    suspend fun getAirQuality(@QueryMap params : Map<String,String>): Response<AirQualityResponse>

    @GET(RLTM_STATION)
    suspend fun getRltmStation(@QueryMap params : Map<String,String>): Response<RltmStationResponse>

    @GET(STATION_FIND)
    suspend fun getStationFind(@QueryMap params : Map<String,String>): Response<StationFindResponse>
}