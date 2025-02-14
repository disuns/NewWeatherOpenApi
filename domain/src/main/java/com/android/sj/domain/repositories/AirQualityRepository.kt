package com.android.sj.domain.repositories

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import kotlinx.coroutines.flow.Flow

interface AirQualityRepository {
    fun fetchAirQuality(airQualityDate: String) : Flow<ApiResult<AirQualityData>>
    fun fetchRltmStation(stationName: String) : Flow<ApiResult<RltmStationData>>
    fun fetchStationFind(regionX: String, regionY: String) : Flow<ApiResult<StationFindData>>
}