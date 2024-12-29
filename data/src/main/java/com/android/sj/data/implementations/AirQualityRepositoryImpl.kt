package com.android.sj.data.implementations

import com.android.sj.data.remote.datasource.AirQualityDataSource
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.domain.repositories.AirQualityRepository
import com.test.data.mapper.DataMapper
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AirQualityRepositoryImpl @Inject constructor(
    private val airQualityDataSource : AirQualityDataSource,
    private val mapper : DataMapper
) : AirQualityRepository {
    override fun fetchAirQuality(params: Map<String, String>) =
        mapper.responseToDomainAirQuality(airQualityDataSource.fetchAirQuality(params))

    override fun fetchRltmStation(params: Map<String, String>) =
        mapper.responseToDomainRltmStation(airQualityDataSource.fetchRltmStation(params))

    override fun fetchStationFind(params: Map<String, String>) =
        mapper.responseToDomainStationFind(airQualityDataSource.fetchStationFind(params))
}