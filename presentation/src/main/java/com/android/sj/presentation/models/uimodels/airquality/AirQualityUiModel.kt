package com.android.sj.presentation.models.uimodels.airquality

data class AirQualityUiModel(
    val dataTimeAndCode: String,
    val overall: String,
    val cause: String,
    val actionKnack: String?,
    val informGrades: MutableList<String>,
    val imageUrl1: String,
    val imageUrl2: String,
    val imageUrl3: String
    )
