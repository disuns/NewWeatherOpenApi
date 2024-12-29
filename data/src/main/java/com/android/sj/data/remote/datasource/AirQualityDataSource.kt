package com.android.sj.data.remote.datasource

import com.android.sj.data.remote.response.datapotal.AirQualityResponse
import com.android.sj.data.remote.response.datapotal.RltmStationResponse
import com.android.sj.data.remote.response.datapotal.StationFindResponse
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.QueryMap

interface AirQualityDataSource {
    fun fetchAirQuality(@QueryMap params: Map<String, String>) : Flow<ApiResult<AirQualityResponse>>
    fun fetchRltmStation(@QueryMap params: Map<String, String>) : Flow<ApiResult<RltmStationResponse>>
    fun fetchStationFind(@QueryMap params: Map<String, String>) : Flow<ApiResult<StationFindResponse>>
}