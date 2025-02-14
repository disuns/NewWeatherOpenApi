package com.android.sj.data.impl.repositorys

import com.android.sj.data.mapper.AirQualityDataMapper
import com.android.sj.data.network.datasource.AirQualityDataSource
import com.android.sj.data.network.request.datapotal.AirQualityRequest
import com.android.sj.data.network.request.datapotal.RltmStationRequest
import com.android.sj.data.network.request.datapotal.StationFindRequest
import com.android.sj.data.network.request.datapotal.toMap
import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.domain.repositories.AirQualityRepository
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class AirQualityRepositoryImpl @Inject constructor(
    private val airQualityDataSource : AirQualityDataSource,
    private val mapper : AirQualityDataMapper
) : AirQualityRepository {
    override fun fetchAirQuality(airQualityDate: String): Channel<ApiResult<AirQualityData>> {
        val request = AirQualityRequest(searchDate = airQualityDate)
        return mapper.responseToDomainAirQuality(airQualityDataSource.fetchAirQuality(request.toMap()))
    }

    override fun fetchRltmStation(stationName: String): Channel<ApiResult<RltmStationData>> {
        val request = RltmStationRequest(stationName = stationName)
        return mapper.responseToDomainRltmStation(airQualityDataSource.fetchRltmStation(request.toMap()))
    }

    override fun fetchStationFind(regionX: String, regionY: String): Channel<ApiResult<StationFindData>> {
        val request = StationFindRequest(tmX = regionX, tmY = regionY)
        return mapper.responseToDomainStationFind(airQualityDataSource.fetchStationFind(request.toMap()))
    }
}