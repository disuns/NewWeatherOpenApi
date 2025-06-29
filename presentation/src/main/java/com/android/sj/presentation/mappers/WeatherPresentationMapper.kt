package com.android.sj.presentation.mappers

import android.content.Context
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.TimeWeatherData
import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.android.sj.presentation.enum.imgConvert
import com.android.sj.presentation.models.uimodels.weather.TimeWeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeatherUIModel
import com.android.sj.presentation.models.uimodels.weather.WeekRainSkyUIModel
import com.android.sj.presentation.utils.DataConstants.RAIN_MM
import com.android.sj.presentation.utils.DataConstants.RAIN_MM_NOW
import com.android.sj.presentation.utils.DataConstants.RAIN_PER
import com.android.sj.presentation.utils.DataConstants.RAIN_TYPE
import com.android.sj.presentation.utils.DataConstants.SKY
import com.android.sj.presentation.utils.DataConstants.TMP_NOW
import com.android.sj.presentation.utils.DataConstants.TMP_TIME
import com.android.sj.presentation.utils.DataConstants.WET
import com.android.sj.presentation.utils.DataConstants.WIND_DIR
import com.android.sj.presentation.utils.DataConstants.WIND_POWER
import com.android.sj.presentation.utils.dateConvert
import com.android.sj.presentation.utils.managers.TimeManager
import com.android.sj.presentation.utils.nowRainConvert
import com.android.sj.presentation.utils.nowWetConvert
import com.android.sj.presentation.utils.rainConvert
import com.android.sj.presentation.utils.rainPerConvert
import com.android.sj.presentation.utils.skyConvert
import com.android.sj.presentation.utils.skyImgEnum
import com.android.sj.presentation.utils.tempConvert
import com.android.sj.presentation.utils.timeDataConvert
import com.android.sj.presentation.utils.weatherRainImgConvert
import com.android.sj.presentation.utils.wetConvert
import com.android.sj.presentation.utils.windDir
import com.android.sj.presentation.utils.windPower
import dagger.assisted.AssistedInject

class WeatherPresentationMapper @AssistedInject constructor(
    private val context: Context
) : BaseMapper() {
    fun domainToUIWeather(data : WeatherData) = WeatherUIModel().apply{
        data.items.forEach { item ->
            when (item.category) {
                TMP_NOW -> nowTemp = item.value.tempConvert(context)
                RAIN_MM_NOW -> nowRain = item.value.nowRainConvert(context)
                WET -> nowWet = item.value.nowWetConvert(context)
                WIND_DIR -> windDir = item.value.windDir(context)
                WIND_POWER -> nowWind =
                    item.value.windPower(context, windDir)

                RAIN_TYPE -> weatherImg = item.value.weatherRainImgConvert()
                SKY -> {
                    weatherImgDrawable =
                        item.value.skyImgEnum(weatherImg).imgConvert(context)
                    weatherText = item.value.skyConvert(context)
                }
            }
        }
    }

    fun domainToUITimeWeather(data : TimeWeatherData)= TimeWeatherUIModel(
        data.items.groupBy { it.weatherDate to it.weatherTime }
            .map { (_,items)->
                TimeWeatherUIModel.Item(
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
    )

    fun domainToUIWeekRainSky(data: WeekRainSkyData) = WeekRainSkyUIModel(
        data.items.flatMap { item ->
            (0..3).map { dayIndex ->
                setWeekWeatherData(
                    weekDate = TimeManager(context).getWeatherWeekUIDate(dayIndex+4),
                    rainAM = item.rnStAm[dayIndex].toString().rainPerConvert(context),
                    rainPM = item.rnStPm[dayIndex].toString().rainPerConvert(context),
                    skyAM = item.wfAm[dayIndex],
                    skyPM = item.wfPm[dayIndex]
                )
            }
        }.toMutableList()
    )

    private fun setWeekWeatherData(
        weekDate: String,
        rainAM: String,
        rainPM: String,
        skyAM: String,
        skyPM: String
    ): WeekRainSkyUIModel.Item {
        return WeekRainSkyUIModel.Item().apply {
            this.weekDate = weekDate
            this.rainAm = rainAM
            this.rainPm = rainPM
            this.skyAm = skyAM
            this.skyPm = skyPM
        }
    }
}