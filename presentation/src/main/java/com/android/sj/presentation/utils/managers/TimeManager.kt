package com.android.sj.presentation.utils.managers

import android.annotation.SuppressLint
import android.content.Context
import com.android.sj.presentation.R
import com.android.sj.presentation.common.models.etc.WeekDate
import com.android.sj.presentation.utils.weekDateConvert
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("SimpleDateFormat")
@Singleton
class TimeManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val dateFormat = SimpleDateFormat("yyyyMMdd")
    private val timeFormat = SimpleDateFormat("HH")
    private val timeFormatWithMinutes = SimpleDateFormat("HHmm")
    private val airQualityDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val weekFormatDay = SimpleDateFormat("yyyyMMdd0600")
    private val weekFormatNight = SimpleDateFormat("yyyyMMdd1800")
    private val monthFormat = SimpleDateFormat("MM")
    private val dayFormat = SimpleDateFormat("dd")

    private fun getCurrentCalendar() = Calendar.getInstance()

    fun urlNowDate(): String {
        val now = getCurrentCalendar().apply {
            if (get(Calendar.HOUR_OF_DAY) < 1) {
                add(Calendar.HOUR_OF_DAY, -2)
            }
        }
        return dateFormat.format(now.time)
    }

    fun urlNowTime(): String {
        val now = getCurrentCalendar()
        val currentMinute = now[Calendar.MINUTE]

        if (currentMinute >= 45) {
            now[Calendar.MINUTE] = 30
        } else {
            now.add(Calendar.HOUR_OF_DAY, -1)
            now[Calendar.MINUTE] = 30
            if (now[Calendar.HOUR_OF_DAY] == 23) {
                now.add(Calendar.DAY_OF_MONTH, -1)
            }
        }

        return timeFormatWithMinutes.format(now.time)
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
        val currentHour = getCurrentCalendar()[Calendar.HOUR_OF_DAY]
        val currentMinute = getCurrentCalendar()[Calendar.MINUTE]
        val currentTotalMinutes = currentHour * 60 + currentMinute

        val baseTimes = listOf(
            2 * 60 + 10 to "0200",
            5 * 60 + 10 to "0500",
            8 * 60 + 10 to "0800",
            11 * 60 + 10 to "1100",
            14 * 60 + 10 to "1400",
            17 * 60 + 10 to "1700",
            20 * 60 + 10 to "2000",
            23 * 60 + 10 to "2300"
        )

        return baseTimes.lastOrNull { currentTotalMinutes >= it.first }?.second ?: "2300"
    }

    fun urlWeekWeatherTime(): String {
        val hour = timeFormat.format(getCurrentCalendar().time).toInt()


        if (hour !in 6..18) {
            val adjustedCalendar = getCurrentCalendar()
            adjustedCalendar.add(Calendar.DATE, if (hour < 6) -1 else 0)
        }

        return if (hour in 6..18) {
            weekFormatDay.format(getCurrentCalendar().time)
        } else {
            weekFormatNight.format(getCurrentCalendar().time)
        }
    }

    fun getWeatherWeekUIDate(dayLater: Int): String {
        val calendar = getCurrentCalendar().apply {
            add(Calendar.DAY_OF_MONTH, dayLater)
        }

        val week = context.resources.getStringArray(R.array.Week)
        val dayOfWeekIndex = calendar[Calendar.DAY_OF_WEEK] - 1

        val weekDay = if (dayOfWeekIndex in week.indices) {
            week[dayOfWeekIndex]
        } else {
            week[Calendar.SATURDAY - 1]
        }

        return WeekDate(monthFormat.format(calendar.time), dayFormat.format(calendar.time), weekDay).weekDateConvert(context)
    }

    fun urlAirQualityDate(): String {
        val hour = getCurrentCalendar()[Calendar.HOUR_OF_DAY]

        val targetDate = if (hour in 23..23 || hour in 0..4) {
            getCurrentCalendar().apply { add(Calendar.DAY_OF_MONTH, -1) }.time
        } else {
            getCurrentCalendar().time
        }

        return airQualityDateFormat.format(targetDate)
    }
}