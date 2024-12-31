package com.android.sj.data.implementations

import com.android.sj.data.remote.datasource.WeatherDataSource
import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.data.mapper.DataMapper
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val mapper : DataMapper
) : WeatherRepository {
    override fun fetchWeather(params: Map<String, String>) =
        mapper.responseToDomainWeather(weatherDataSource.fetchTimeWeather(params))
    override fun fetchTimeWeather(params: Map<String, String>) =
        mapper.responseToDomainTimeWeather(weatherDataSource.fetchTimeWeather(params))
    override fun fetchWeekRainSky(params: Map<String, String>) =
        mapper.responseToDomainRainSky(weatherDataSource.fetchWeekRainSky(params))
}