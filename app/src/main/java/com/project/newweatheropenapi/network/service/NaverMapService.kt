package com.project.newweatheropenapi.network.service

import com.project.newweatheropenapi.MAP_REVERSE_GEOCODE
import com.sjchoi.weather.dataclass.reverseGeocoder.ReverseGeocoder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapService {
    @GET(MAP_REVERSE_GEOCODE)
    suspend fun requestReverseGeoCo(@Query("request") request: String,
                                    @Query("coords", encoded = true) coords:String,
                                    @Query("sourcecrs", encoded = true) sourcecrs:String,
                                    @Query("targetcrs", encoded = true) targetcrs:String,
                                    @Query("output") output:String,
                                    @Query("orders", encoded = true) orders:String,
                                    @Query("X-NCP-APIGW-API-KEY-ID") apikeyid:String,
                                    @Query("X-NCP-APIGW-API-KEY") apikey:String): Response<ReverseGeocoder>
}