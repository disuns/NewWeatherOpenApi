package com.android.sj.data.mapper

import com.android.sj.common.NO_ERROR
import com.android.sj.data.network.response.datapotal.AirQualityResponse
import com.android.sj.data.network.response.datapotal.RltmStationResponse
import com.android.sj.data.network.response.datapotal.StationFindResponse
import com.android.sj.domain.ApiResult
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AirQualityDataMapper @Inject constructor() : BaseMapper() {
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
}