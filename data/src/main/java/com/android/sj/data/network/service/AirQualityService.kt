package com.android.sj.data.network.service

import com.android.sj.data.network.ApiConstants.AIR_QUALITY_FRCST
import com.android.sj.data.network.ApiConstants.RLTM_STATION
import com.android.sj.data.network.ApiConstants.STATION_FIND
import com.android.sj.data.network.response.datapotal.AirQualityResponse
import com.android.sj.data.network.response.datapotal.RltmStationResponse
import com.android.sj.data.network.response.datapotal.StationFindResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AirQualityService {
    @GET(AIR_QUALITY_FRCST)
    suspend fun fetchAirQuality(@QueryMap params : Map<String,String>): Response<AirQualityResponse>

    @GET(RLTM_STATION)
    suspend fun fetchRltmStation(@QueryMap params : Map<String,String>): Response<RltmStationResponse>

    @GET(STATION_FIND)
    suspend fun fetchStationFind(@QueryMap params : Map<String,String>): Response<StationFindResponse>
}