package com.android.sj.presentation.mappers

import android.content.Context
import com.android.sj.domain.ApiResult
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiData
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIData
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIData.MeasuringData
import com.android.sj.presentation.models.uimodels.airquality.StationFindUIData
import com.android.sj.presentation.utils.airDateAndCode
import com.android.sj.presentation.utils.rltmStationDate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AirQualityPresentationMapper @Inject constructor(
    private val context: Context
) : BaseMapper() {
    fun domainToUIAirQuality(flow: Flow<ApiResult<AirQualityData>>): Flow<ApiResult<AirQualityUiData>> {
        return apiResultMapper(flow) {
            ApiResult.Success(
                AirQualityUiData(
                    dataTimeAndCode = it.code.airDateAndCode(it.dataTime, context),
                    overall = it.overall,
                    cause = it.cause,
                    actionKnack = it.actionKnack,
                    informGrades = it.informGrade.split(",").toMutableList(),
                    imageUrl1 = it.imageUrl1,
                    imageUrl2 = it.imageUrl2,
                    imageUrl3 = it.imageUrl3
                )
            )
        }
    }

    fun domainToUIRltmStation(flow: Flow<ApiResult<RltmStationData>>): Flow<ApiResult<RltmStationUIData>> {
        return apiResultMapper(flow) {
            ApiResult.Success(
                RltmStationUIData(
                    dataTime = it.dataTime.rltmStationDate(context),
                    measuringData = mutableListOf(
                        MeasuringData(it.khaiValue, it.khaiGrade, null),
                        MeasuringData(it.pm25Value, it.pm25Grade, it.pm25Flag),
                        MeasuringData(it.pm10Value, it.pm10Grade, it.pm10Flag),
                        MeasuringData(it.o3Value, it.o3Grade, it.o3Flag),
                        MeasuringData(it.coValue, it.coGrade, it.coFlag),
                        MeasuringData(it.no2Value, it.no2Grade, it.no2Flag),
                        MeasuringData(it.so2Value, it.so2Grade, it.so2Flag)
                    )
                )
            )
        }
    }

    fun domainToUIStationFind(flow: Flow<ApiResult<StationFindData>>): Flow<ApiResult<StationFindUIData>> {
        return apiResultMapper(flow) {
            ApiResult.Success(
                StationFindUIData(
                    stationName = it.stationName ?: "정보없음"
                )
            )
        }
    }
}