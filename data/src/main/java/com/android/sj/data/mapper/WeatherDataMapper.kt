package com.android.sj.data.mapper

import com.android.sj.common.NO_ERROR
import com.android.sj.data.network.response.datapotal.WeatherResponse
import com.android.sj.data.network.response.datapotal.WeekRainSkyResponse
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.TimeWeatherData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDataMapper @Inject constructor() : BaseMapper() {
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
                            rnStAm = mutableListOf(itemData.rnSt4Am, itemData.rnSt5Am, itemData.rnSt6Am, itemData.rnSt7Am),
                            rnStPm = mutableListOf(itemData.rnSt4Pm, itemData.rnSt5Pm, itemData.rnSt6Pm, itemData.rnSt7Pm),
                            wfAm = mutableListOf(itemData.wf4Am, itemData.wf5Am, itemData.wf6Am, itemData.wf7Am),
                            wfPm = mutableListOf(itemData.wf4Pm, itemData.wf5Pm, itemData.wf6Pm, itemData.wf7Pm)
                        )
                    )
                }

                ApiResult.Success(WeekRainSkyData(list))
            }
        }
    }
}