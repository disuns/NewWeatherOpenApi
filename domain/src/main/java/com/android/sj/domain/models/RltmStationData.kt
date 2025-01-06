package com.android.sj.domain.models

data class RltmStationData(
    val dataTime: String,
    val khaiValue: String,
    val khaiGrade: String,
    val pm25Value: String,
    val pm25Grade: String,
    val pm25Flag: String?,
    val pm10Value: String,
    val pm10Grade: String,
    val pm10Flag: String?,
    val o3Value: String,
    val o3Grade: String,
    val o3Flag: String?,
    val coValue: String,
    val coGrade: String,
    val coFlag: String?,
    val no2Value: String,
    val no2Grade: String,
    val no2Flag: String?,
    val so2Value: String,
    val so2Grade: String,
    val so2Flag: String?
)