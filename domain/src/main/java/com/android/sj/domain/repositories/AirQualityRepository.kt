package com.android.sj.domain.repositories

import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface AirQualityRepository {
    fun fetchAirQuality(params: Map<String, String>) : Flow<ApiResult<AirQualityData>>
    fun fetchRltmStation(params: Map<String, String>) : Flow<ApiResult<RltmStationData>>
    fun fetchStationFind(params: Map<String, String>) : Flow<ApiResult<StationFindData>>
}