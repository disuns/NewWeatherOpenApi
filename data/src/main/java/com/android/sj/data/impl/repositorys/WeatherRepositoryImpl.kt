package com.android.sj.data.impl.repositorys

import com.android.sj.data.mapper.WeatherDataMapper
import com.android.sj.data.network.datasource.WeatherDataSource
import com.android.sj.data.network.request.datapotal.WeatherRequest
import com.android.sj.data.network.request.datapotal.WeekRainSkyRequest
import com.android.sj.data.network.request.datapotal.toMap
import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.TimeWeatherData
import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.android.sj.domain.repositories.WeatherRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val mapper : WeatherDataMapper
) : WeatherRepository {
    override fun fetchWeather(date: String, time: String, lat: String, lon: String): Flow<ApiResult<WeatherData>> {
        val request = WeatherRequest(baseDate = date, baseTime = time, nx = lat, ny = lon)

        return mapper.responseToDomainWeather(weatherDataSource.fetchWeather(request.toMap()))
    }

    override fun fetchTimeWeather(date: String, time: String, lat: String, lon: String): Flow<ApiResult<TimeWeatherData>> {
        val request = WeatherRequest(baseDate = date, baseTime = time, nx = lat, ny = lon)

        return  mapper.responseToDomainTimeWeather(weatherDataSource.fetchTimeWeather(request.toMap()))
    }

    override fun fetchWeekRainSky(landCode: String, time: String): Flow<ApiResult<WeekRainSkyData>> {
        val request = WeekRainSkyRequest(regId = landCode, tmFc = time)

        return mapper.responseToDomainRainSky(weatherDataSource.fetchWeekRainSky(request.toMap()))
    }

}