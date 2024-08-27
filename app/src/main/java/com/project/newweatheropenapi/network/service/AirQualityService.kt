package com.project.newweatheropenapi.network.service

import com.project.newweatheropenapi.AIR_QUALITY_FRCST
import com.project.newweatheropenapi.RLTM_STATION
import com.project.newweatheropenapi.STATION_FIND
import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.RltmStationIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.StationInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityService {
    @GET(AIR_QUALITY_FRCST)
    suspend fun requestAirQuality(@Query("serviceKey") serviceKey: String,
                                  @Query("returnType") returnType:String,
                                  @Query("pageNo") pageNo:String,
                                  @Query("numOfRows") numOfRows:String,
                                  @Query("searchDate") searchDate:String,
                                  @Query("InformCode") InformCode:String): Response<AirQualityIndex>

    @GET(RLTM_STATION)
    suspend fun requestRltmStation(@Query("serviceKey") serviceKey: String,
                                   @Query("returnType") returnType:String,
                                   @Query("pageNo") pageNo:String,
                                   @Query("numOfRows") numOfRows:String,
                                   @Query("stationName") stationName:String,
                                   @Query("dataTerm") dataTerm:String,
                                   @Query("ver")ver:String): Response<RltmStationIndex>

    @GET(STATION_FIND)
    suspend fun requestStationFind(@Query("serviceKey") serviceKey: String,
                                   @Query("returnType") returnType:String,
                                   @Query("tmX") tmX:String,
                                   @Query("tmY") tmY:String,
                                   @Query("ver")ver:String): Response<StationInfo>
}