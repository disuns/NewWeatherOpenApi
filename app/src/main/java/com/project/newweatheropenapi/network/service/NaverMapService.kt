package com.project.newweatheropenapi.network.service

import com.project.newweatheropenapi.common.MAP_CLIENT_KEY
import com.project.newweatheropenapi.common.MAP_CLIENT_KEY_ID
import com.project.newweatheropenapi.common.MAP_REVERSE_GEOCODE
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NaverMapService {
    @GET(MAP_REVERSE_GEOCODE)
    suspend fun getReverseGeoCo(@QueryMap params : Map<String,String>): Response<NaverMapResponse>
}