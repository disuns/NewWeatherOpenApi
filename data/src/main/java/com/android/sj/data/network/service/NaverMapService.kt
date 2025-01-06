package com.android.sj.data.network.service

import com.android.sj.data.network.ApiConstants.MAP_REVERSE_GEOCODE
import com.android.sj.data.network.response.navermap.NaverMapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NaverMapService {
    @GET(MAP_REVERSE_GEOCODE)
    suspend fun fetchReverseGeoCo(@QueryMap params : Map<String,String>): Response<NaverMapResponse>
}