package com.android.sj.presentation.common.mappers

import android.content.Context
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.presentation.common.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.common.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.common.models.uimodels.airquality.RltmStationUIModel.MeasuringData
import com.android.sj.presentation.common.models.uimodels.airquality.StationFindUIModel
import com.android.sj.presentation.utils.airDateAndCode
import com.android.sj.presentation.utils.rltmStationDate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AirQualityPresentationMapper @Inject constructor(
    @ApplicationContext private val context: Context,
) : BaseMapper() {
    fun domainToUIAirQuality(data: AirQualityData) = AirQualityUiModel(
            dataTimeAndCode = data.code.airDateAndCode(data.dataTime, context),
            overall = data.overall,
            cause = data.cause,
            actionKnack = data.actionKnack,
            informGrades = data.informGrade.split(",").toMutableList(),
            imageUrl1 = data.imageUrl1,
            imageUrl2 = data.imageUrl2,
            imageUrl3 = data.imageUrl3
        )


    fun domainToUIRltmStation(data: RltmStationData) = RltmStationUIModel(
        dataTime = data.dataTime.rltmStationDate(context),
        measuringData = mutableListOf(
            MeasuringData(data.khaiValue, data.khaiGrade, null),
            MeasuringData(data.pm25Value, data.pm25Grade, data.pm25Flag),
            MeasuringData(data.pm10Value, data.pm10Grade, data.pm10Flag),
            MeasuringData(data.o3Value, data.o3Grade, data.o3Flag),
            MeasuringData(data.coValue, data.coGrade, data.coFlag),
            MeasuringData(data.no2Value, data.no2Grade, data.no2Flag),
            MeasuringData(data.so2Value, data.so2Grade, data.so2Flag)
        )
    )

    fun domainToUIStationFind(data: StationFindData) = StationFindUIModel(
        stationName = data.stationName ?: "정보없음"
    )
}