package com.android.sj.data.network.datasource.impl

import com.android.sj.data.network.datasource.AirQualityDataSource
import com.android.sj.data.network.safeFlow
import com.android.sj.data.network.service.AirQualityService
import retrofit2.http.QueryMap
import javax.inject.Inject

class AirQualityDataSourceImpl @Inject constructor(private val service: AirQualityService) : AirQualityDataSource {
    override fun fetchAirQuality(@QueryMap params: Map<String, String>) = safeFlow { service.fetchAirQuality(params) }
    override fun fetchRltmStation(@QueryMap params: Map<String, String>) = safeFlow { service.fetchRltmStation(params) }
    override fun fetchStationFind(@QueryMap params: Map<String, String>) = safeFlow { service.fetchStationFind(params) }
}