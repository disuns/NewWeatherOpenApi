package com.project.newweatheropenapi.utils.managers

import android.annotation.SuppressLint
import android.content.Context
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.dataclass.WeekDate
import com.project.newweatheropenapi.utils.logMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("SimpleDateFormat")
@Singleton
class TimeManager @Inject constructor(@ApplicationContext private val context: Context){
    private val dateFormat = SimpleDateFormat("yyyyMMdd")
    private val timeFormat = SimpleDateFormat("HH")
    private val timeFormatWithMinutes = SimpleDateFormat("HHmm")
    private val airQualityDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val weekFormatDay = SimpleDateFormat("yyyyMMdd0600")
    private val weekFormatNight = SimpleDateFormat("yyyyMMdd1800")
    private val monthFormat = SimpleDateFormat("MM")
    private val dayFormat = SimpleDateFormat("dd")

    private fun getCurrentCalendar()= Calendar.getInstance()

    fun urlNowDate(): String {
        val now = getCurrentCalendar().apply {
            if (get(Calendar.HOUR_OF_DAY) < 1) {
                add(Calendar.HOUR_OF_DAY, -2) // Subtract 2 hours if hour is less than 1
            }
        }
        return dateFormat.format(now.time)
    }

    fun urlNowTime(): String {
        val now = getCurrentCalendar().apply {
            add(Calendar.MINUTE, -30) // Subtract 30 minutes
        }
        val formattedTime = timeFormatWithMinutes.format(now.time)
        return if (timeFormat.format(now.time).toInt() < 1) {
            "2330"
        } else {
            formattedTime
        }
    }

    fun urlTimeWeatherDate(): String {
        val now = getCurrentCalendar().apply {
            if (get(Calendar.HOUR_OF_DAY) < 2) {
                add(Calendar.DAY_OF_MONTH, -1)
            }
        }
        return dateFormat.format(now.time)
    }

    fun urlTimeWeatherTime(): String {
        val currentHour = timeFormat.format(getCurrentCalendar().time).toInt()

        val timeMap = listOf(
            "2300" to 2,
            "0200" to 5,
            "0500" to 8,
            "0800" to 11,
            "1100" to 14,
            "1400" to 17,
            "1700" to 20,
            "2000" to 23
        )

        return timeMap.findLast { currentHour >= it.second }?.first ?: "2300"
    }

    fun urlWeekWeatherTime(): String {
        val hour = timeFormat.format(getCurrentCalendar().time).toInt()
        return if (hour in 7..18) {
            weekFormatDay.format(getCurrentCalendar().time) // 오전 6시 포맷 사용
        } else {
            weekFormatNight.format(getCurrentCalendar().time) // 오후 6시 포맷 사용
        }
    }

    fun getWeatherWeekUIDate(dayLater: Int): WeekDate {
        val calendar = getCurrentCalendar().apply {
            add(Calendar.DAY_OF_MONTH, dayLater)
        }
        val monthFormat = SimpleDateFormat("MM")
        val dayFormat = SimpleDateFormat("dd")

        val week = context.resources.getStringArray(R.array.Week)
        val dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1

        val weekDay = if (dayOfWeekIndex in week.indices) {
            week[dayOfWeekIndex]
        } else {
            week[Calendar.SATURDAY - 1]
        }

        return WeekDate(monthFormat.format(calendar.time), dayFormat.format(calendar.time), weekDay)
    }

    fun urlAirQualityDate() = airQualityDateFormat.format(getCurrentCalendar().time)
}