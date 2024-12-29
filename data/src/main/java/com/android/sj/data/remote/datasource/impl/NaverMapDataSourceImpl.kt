package com.android.sj.data.remote.datasource.impl

import com.android.sj.data.remote.datasource.NaverMapDataSource
import com.android.sj.data.remote.safeFlow
import com.android.sj.data.remote.service.NaverMapService
import retrofit2.http.QueryMap
import javax.inject.Inject

class NaverMapDataSourceImpl @Inject constructor(private val service: NaverMapService) :
    NaverMapDataSource {
    override fun fetchReverseGeoCo(@QueryMap params: Map<String, String>) = safeFlow { service.fetchReverseGeoCo(params) }
}