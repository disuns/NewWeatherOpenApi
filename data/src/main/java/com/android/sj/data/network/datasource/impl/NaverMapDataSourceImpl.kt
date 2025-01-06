package com.android.sj.data.network.datasource.impl

import com.android.sj.data.network.datasource.NaverMapDataSource
import com.android.sj.data.network.safeFlow
import com.android.sj.data.network.service.NaverMapService
import retrofit2.http.QueryMap
import javax.inject.Inject

class NaverMapDataSourceImpl @Inject constructor(private val service: NaverMapService) :
    NaverMapDataSource {
    override fun fetchReverseGeoCo(@QueryMap params: Map<String, String>) = safeFlow { service.fetchReverseGeoCo(params) }
}