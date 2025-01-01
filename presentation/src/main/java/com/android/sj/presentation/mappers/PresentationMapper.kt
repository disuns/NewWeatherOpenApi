package com.android.sj.presentation.mappers

import android.content.Context
import com.android.sj.domain.ApiResult
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.NaverMapData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.domain.models.TimeWeatherData
import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.android.sj.presentation.RequestConstants.RAIN_MM
import com.android.sj.presentation.RequestConstants.RAIN_MM_NOW
import com.android.sj.presentation.RequestConstants.RAIN_PER
import com.android.sj.presentation.RequestConstants.RAIN_TYPE
import com.android.sj.presentation.RequestConstants.SKY
import com.android.sj.presentation.RequestConstants.TMP_NOW
import com.android.sj.presentation.RequestConstants.TMP_TIME
import com.android.sj.presentation.RequestConstants.WET
import com.android.sj.presentation.RequestConstants.WIND_DIR
import com.android.sj.presentation.RequestConstants.WIND_POWER
import com.android.sj.presentation.dataclass.WeekDate
import com.android.sj.presentation.enum.imgConvert
import com.android.sj.presentation.managers.TimeManager
import com.android.sj.presentation.models.airquality.AirQualityUiData
import com.android.sj.presentation.models.airquality.RltmStationUIData
import com.android.sj.presentation.models.airquality.RltmStationUIData.MeasuringData
import com.android.sj.presentation.models.airquality.StationFindUIData
import com.android.sj.presentation.models.navermap.ReverseGeoUIData
import com.android.sj.presentation.models.weather.TimeWeatherUIData
import com.android.sj.presentation.models.weather.WeatherUIData
import com.android.sj.presentation.models.weather.WeekRainSkyUIData
import com.android.sj.presentation.utils.airDateAndCode
import com.android.sj.presentation.utils.dateConvert
import com.android.sj.presentation.utils.mapAddressConvert
import com.android.sj.presentation.utils.nowRainConvert
import com.android.sj.presentation.utils.nowWetConvert
import com.android.sj.presentation.utils.rainConvert
import com.android.sj.presentation.utils.rainPerConvert
import com.android.sj.presentation.utils.rltmStationDate
import com.android.sj.presentation.utils.rltmTitle
import com.android.sj.presentation.utils.skyConvert
import com.android.sj.presentation.utils.skyImgEnum
import com.android.sj.presentation.utils.tempConvert
import com.android.sj.presentation.utils.timeDataConvert
import com.android.sj.presentation.utils.weatherRainImgConvert
import com.android.sj.presentation.utils.wetConvert
import com.android.sj.presentation.utils.windDir
import com.android.sj.presentation.utils.windPower
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PresentationMapper @Inject constructor(
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

    fun domainToUIReverseGeoCo(flow: Flow<ApiResult<NaverMapData>>): Flow<ApiResult<ReverseGeoUIData>> {
        return apiResultMapper(flow) {
            ApiResult.Success(
                ReverseGeoUIData(
                    mapAddress = it.mapAddressConvert(context),
                    centerX = it.centerX.toString(),
                    centerY = it.centerY.toString()
                )
            )
        }
    }

    fun domainToUIWeather(flow: Flow<ApiResult<WeatherData>>): Flow<ApiResult<WeatherUIData>> {
        return apiResultMapper(flow) {
            val weatherData = WeatherUIData()
            it.items.forEach { item->
                when (item.category) {
                    TMP_NOW -> weatherData.nowTemp = item.value.tempConvert(context)
                    RAIN_MM_NOW -> weatherData.nowRain = item.value.nowRainConvert(context)
                    WET -> weatherData.nowWet = item.value.nowWetConvert(context)
                    WIND_DIR -> weatherData.windDir = item.value.windDir(context)
                    WIND_POWER -> weatherData.nowWind = item.value.windPower(context, weatherData.windDir)
                    RAIN_TYPE -> weatherData.weatherImg = item.value.weatherRainImgConvert()
                    SKY -> {
                        weatherData.weatherImgDrawable =
                            item.value.skyImgEnum(weatherData.weatherImg).imgConvert(context)
                        weatherData.weatherText = item.value.skyConvert(context)
                    }
                }
            }

            ApiResult.Success(weatherData)
        }
    }

    fun domainToUITimeWeather(flow: Flow<ApiResult<TimeWeatherData>>): Flow<ApiResult<TimeWeatherUIData>> {
        return apiResultMapper(flow) { item ->
            val list = item.items.groupBy { it.weatherDate to it.weatherTime }
                .map { (_, items) ->
                    TimeWeatherUIData.Item(
                        weatherTime = (items.first().weatherTime).timeDataConvert(context),
                        weatherDate = (items.first().weatherDate).dateConvert(context),
                        temp = (items.find { it.category == TMP_TIME }?.value ?: "").tempConvert(context),
                        windDir = (items.find { it.category == WIND_DIR }?.value ?: "").windDir(context),
                        windPower = (items.find { it.category == WIND_POWER }?.value ?: "").windPower(context),
                        rainPer = (items.find { it.category == RAIN_PER }?.value ?: "").rainPerConvert(context),
                        rainMm = (items.find { it.category == RAIN_MM }?.value ?: "").rainConvert(context),
                        wet = (items.find { it.category == WET }?.value ?: "").wetConvert(context),
                        imgDrawable = items.find { it.category == SKY }?.value?.skyImgEnum(
                            (items.find { it.category == RAIN_TYPE }?.value ?: "").weatherRainImgConvert()
                        )?.imgConvert(context)
                    )
                }.toMutableList()

            ApiResult.Success(TimeWeatherUIData(list))
        }
    }

    fun domainToUIWeekRainSky(flow: Flow<ApiResult<WeekRainSkyData>>): Flow<ApiResult<WeekRainSkyUIData>> {
        val timeManager = TimeManager(context)

        return apiResultMapper(flow) { data ->
            val uiList = data.items.flatMap { item ->
                (0..4).map { dayIndex ->
                    setWeekWeatherData(
                        weekDate = timeManager.getWeatherWeekUIDate(dayIndex+3),
                        rainAM = item.rnStAm[dayIndex].toString().rainPerConvert(context),
                        rainPM = item.rnStPm[dayIndex].toString().rainPerConvert(context),
                        skyAM = item.wfAm[dayIndex],
                        skyPM = item.wfPm[dayIndex]
                    )
                }
            }.toMutableList()

            ApiResult.Success(WeekRainSkyUIData(uiList))
        }
    }
    private fun setWeekWeatherData(
        weekDate: String,
        rainAM: String,
        rainPM: String,
        skyAM: String,
        skyPM: String
    ): WeekRainSkyUIData.Item {
        return WeekRainSkyUIData.Item().apply {
            this.weekDate = weekDate
            this.rainAm = rainAM
            this.rainPm = rainPM
            this.skyAm = skyAM
            this.skyPm = skyPM
        }
    }
}