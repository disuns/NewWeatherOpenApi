package com.android.sj.data.network.datasource

import com.android.sj.data.network.response.datapotal.AirQualityResponse
import com.android.sj.data.network.response.datapotal.RltmStationResponse
import com.android.sj.data.network.response.datapotal.StationFindResponse
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import retrofit2.http.QueryMap

interface AirQualityDataSource {
    fun fetchAirQuality(@QueryMap params: Map<String, String>) : Channel<ApiResult<AirQualityResponse>>
    fun fetchRltmStation(@QueryMap params: Map<String, String>) : Channel<ApiResult<RltmStationResponse>>
    fun fetchStationFind(@QueryMap params: Map<String, String>) : Channel<ApiResult<StationFindResponse>>
}