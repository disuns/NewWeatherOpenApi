package com.android.sj.presentation.models.airquality

data class AirQualityUiData(
    val dataTimeAndCode: String,
    val overall: String,
    val cause: String,
    val actionKnack: String?,
    val informGrades: MutableList<String>,
    val imageUrl1: String,
    val imageUrl2: String,
    val imageUrl3: String
    )
