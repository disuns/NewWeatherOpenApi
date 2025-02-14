package com.android.sj.domain.repositories

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import kotlinx.coroutines.channels.Channel

interface AirQualityRepository {
    fun fetchAirQuality(airQualityDate: String) : Channel<ApiResult<AirQualityData>>
    fun fetchRltmStation(stationName: String) : Channel<ApiResult<RltmStationData>>
    fun fetchStationFind(regionX: String, regionY: String) : Channel<ApiResult<StationFindData>>
}