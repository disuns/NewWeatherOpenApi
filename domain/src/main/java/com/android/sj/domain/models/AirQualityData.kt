package com.android.sj.domain.models

data class AirQualityData(
    val dataTime: String,
    val code: String,
    val overall: String,
    val cause: String,
    val actionKnack: String?,
    val informGrade: String,
    val imageUrl1: String,
    val imageUrl2: String,
    val imageUrl3: String,
)
