package com.project.newweatheropenapi.network.service

import com.project.newweatheropenapi.NOW_FCST
import com.project.newweatheropenapi.VILAGE_FCST
import com.project.newweatheropenapi.WEEK_RAIN_SKY
import com.project.newweatheropenapi.network.ApiModule
import com.sjchoi.weather.dataclass.datapotal.fcstdata.FcstData
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekRainSkyData
import retrofit2.Response
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET(VILAGE_FCST)
    suspend fun getWeather(@Query("serviceKey") serviceKey: String,
                               @Query("pageNo") pageNo:String,
                               @Query("numOfRows") numOfRows:String,
                               @Query("dataType") dataType:String,
                               @Query("base_date") base_date:String,
                               @Query("base_time") base_time:String,
                               @Query("nx") nx:String,
                               @Query("ny") ny:String): Response<FcstData>

    @GET(NOW_FCST)
    suspend fun getNowWeather(@Query("serviceKey") serviceKey: String,
                                  @Query("pageNo") pageNo:String,
                                  @Query("numOfRows") numOfRows:String,
                                  @Query("dataType") dataType:String,
                                  @Query("base_date") base_date:String,
                                  @Query("base_time") base_time:String,
                                  @Query("nx") nx:String,
                                  @Query("ny") ny:String): Response<FcstData>

    @GET(WEEK_RAIN_SKY)
    suspend fun getWeekRainSky(@Query("serviceKey") serviceKey: String,
                                   @Query("pageNo") pageNo:String,
                                   @Query("numOfRows") numOfRows:String,
                                   @Query("dataType") dataType:String,
                                   @Query("regId") regId:String,
                                   @Query("tmFc") tmFc:String): Response<WeekRainSkyData>
}