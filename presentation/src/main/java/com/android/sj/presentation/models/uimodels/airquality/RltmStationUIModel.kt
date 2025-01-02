package com.android.sj.presentation.models.uimodels.airquality

data class RltmStationUIModel(
    val dataTime : String,
    val measuringData : MutableList<MeasuringData>
){
    data class MeasuringData(
        val data1: String?,
        val data2: String?,
        val data3: String?
    )
}
