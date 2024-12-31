package com.android.sj.data.implementations

import com.android.sj.data.remote.datasource.AirQualityDataSource
import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.data.mapper.DataMapper
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