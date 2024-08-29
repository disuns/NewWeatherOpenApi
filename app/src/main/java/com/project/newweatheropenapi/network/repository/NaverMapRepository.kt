package com.project.newweatheropenapi.network.repository

import com.project.newweatheropenapi.network.safeFlow
import com.project.newweatheropenapi.network.service.NaverMapService
import retrofit2.http.QueryMap
import javax.inject.Inject

class NaverMapRepository @Inject constructor(private val service: NaverMapService) {
    fun getReverseGeoCo(@QueryMap params: Map<String, String>) = safeFlow { service.getReverseGeoCo(params) }
}
