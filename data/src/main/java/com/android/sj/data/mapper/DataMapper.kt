package com.android.sj.data.mapper

import com.android.sj.common.NO_ERROR
import com.android.sj.data.remote.response.datapotal.AirQualityResponse
import com.android.sj.data.remote.response.datapotal.RltmStationResponse
import com.android.sj.data.remote.response.datapotal.StationFindResponse
import com.android.sj.data.remote.response.datapotal.WeatherResponse
import com.android.sj.data.remote.response.datapotal.WeekRainSkyResponse
import com.android.sj.data.remote.response.navermap.NaverMapResponse
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.NaverMapData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.TimeWeatherData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataMapper @Inject constructor() : BaseMapper() {
    fun responseToDomainAirQuality(response: Flow<ApiResult<AirQualityResponse>>): Flow<ApiResult<AirQualityData>> {
        return apiResultMapper(response) {
            if (it.response.header.resultCode != NO_ERROR) {
                ApiResult.Error(it.response.header.resultCode.toInt(), Throwable("PotalError"))
            } else {
                val data = it.response.body.items[0]
                ApiResult.Success(
                    AirQualityData(
                        dataTime = data.dataTime,
                        code = data.informCode,
                        overall = data.informOverall,
                        cause = data.informCause,
                        actionKnack = data.actionKnack,
                        informGrade = data.informGrade,
                        imageUrl1 = data.imageUrl1,
                        imageUrl2 = data.imageUrl2,
                        imageUrl3 = data.imageUrl3
                    )
                )
            }
        }
    }

    fun responseToDomainRltmStation(response: Flow<ApiResult<RltmStationResponse>>): Flow<ApiResult<RltmStationData>> {
        return apiResultMapper(response) {
            if (it.response.header.resultCode != NO_ERROR) {
                ApiResult.Error(it.response.header.resultCode.toInt(), Throwable("PotalError"))
            } else {
                val data = it.response.body.items[0]
                ApiResult.Success(
                    RltmStationData(
                        dataTime = data.dataTime,
                        khaiValue = data.khaiValue,
                        khaiGrade = data.khaiGrade,
                        pm25Value = data.pm25Value,
                        pm25Grade = data.pm25Grade,
                        pm25Flag = data.pm25Flag,
                        pm10Value = data.pm10Value,
                        pm10Grade = data.pm10Grade,
                        pm10Flag = data.pm10Flag,
                        o3Value = data.o3Value,
                        o3Grade = data.o3Grade,
                        o3Flag = data.o3Flag,
                        coValue = data.coValue,
                        coGrade = data.coGrade,
                        coFlag = data.coFlag,
                        no2Value = data.no2Value,
                        no2Grade = data.no2Grade,
                        no2Flag = data.no2Flag,
                        so2Value = data.so2Value,
                        so2Grade = data.so2Grade,
                        so2Flag = data.so2Flag
                    )
                )
            }
        }
    }

    fun responseToDomainStationFind(response: Flow<ApiResult<StationFindResponse>>): Flow<ApiResult<StationFindData>> {
        return apiResultMapper(response) {
            if (it.response.header.resultCode != NO_ERROR) {
                ApiResult.Error(it.response.header.resultCode.toInt(), Throwable("PotalError"))
            } else {
                val stationItems = it.response.body?.items
                val stationName = stationItems?.firstOrNull()?.stationName
                ApiResult.Success(
                    StationFindData(stationName = stationName)
                )
            }
        }
    }

    fun responseToDomainReverseGeoCo(response: Flow<ApiResult<NaverMapResponse>>): Flow<ApiResult<NaverMapData>> {
        return apiResultMapper(response) {
            if (it.status.code != 0) {
                ApiResult.Error(it.status.code)
            } else {
                val last = it.results.last()
                val center = last.region.area3.coords.center
                ApiResult.Success(
                    NaverMapData(
                        regionArea1Name = last.region.area1.name,
                        regionArea2Name = last.region.area2.name,
                        regionArea3Name = last.region.area3.name,
                        landName = last.land.name,
                        landNumber = last.land.number1,
                        resultName = last.name,
                        centerX = center.x,
                        centerY = center.y
                    )
                )
            }
        }
    }

    fun responseToDomainWeather(response: Flow<ApiResult<WeatherResponse>>): Flow<ApiResult<WeatherData>> {
        return apiResultMapper(response) {
            if (it.response.header.resultCode != NO_ERROR) {
                ApiResult.Error(it.response.header.resultCode.toInt(), Throwable("PotalError"))
            } else {
                val items = it.response.body.items.item
                val list = mutableListOf<WeatherData.Item>()
                items.forEach { item->
                    if (item.fcstTime.toInt() - item.baseTime.toInt() < 100) {
                        list.add(WeatherData.Item(item.category, item.fcstValue ))
                    }
                }
                ApiResult.Success(WeatherData(list))
            }
        }
    }

    fun responseToDomainTimeWeather(response: Flow<ApiResult<WeatherResponse>>): Flow<ApiResult<TimeWeatherData>> {
        return apiResultMapper(response) {
            if (it.response.header.resultCode != NO_ERROR) {
                ApiResult.Error(it.response.header.resultCode.toInt(), Throwable("PotalError"))
            } else {
                val items = it.response.body.items.item
                val list = mutableListOf<TimeWeatherData.Item>()
                items.forEach { item ->
                    val data = TimeWeatherData.Item(
                        weatherDate = item.fcstDate,
                        weatherTime = item.fcstTime,
                        category = item.category,
                        value = item.fcstValue
                    )
                    list.add(data)
                }
                ApiResult.Success(TimeWeatherData(list))
            }
        }
    }

    fun responseToDomainRainSky(response: Flow<ApiResult<WeekRainSkyResponse>>): Flow<ApiResult<WeekRainSkyData>> {
        return apiResultMapper(response) {
            if (it.response.header.resultCode != NO_ERROR) {
                ApiResult.Error(it.response.header.resultCode.toInt(), Throwable("PotalError"))
            } else {
                val item = it.response.body.items.item
                val list = mutableListOf<WeekRainSkyData.Item>()

                item.forEach { itemData->
                    list.add(
                        WeekRainSkyData.Item(
                            rnStAm = mutableListOf(itemData.rnSt3Am, itemData.rnSt4Am, itemData.rnSt5Am, itemData.rnSt6Am, itemData.rnSt7Am),
                            rnStPm = mutableListOf(itemData.rnSt3Pm, itemData.rnSt4Pm, itemData.rnSt5Pm, itemData.rnSt6Pm, itemData.rnSt7Pm),
                            wfAm = mutableListOf(itemData.wf3Am, itemData.wf4Am, itemData.wf5Am, itemData.wf6Am, itemData.wf7Am),
                            wfPm = mutableListOf(itemData.wf3Pm, itemData.wf4Pm, itemData.wf5Pm, itemData.wf6Pm, itemData.wf7Pm)
                        )
                    )
                }

                ApiResult.Success(WeekRainSkyData(list))
            }
        }
    }
}