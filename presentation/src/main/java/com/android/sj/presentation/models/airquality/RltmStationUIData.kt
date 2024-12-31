package com.android.sj.presentation.models.airquality

data class RltmStationUIData(
    val dataTime : String,
    val measuringData : MutableList<MeasuringData>
){
    data class MeasuringData(
        val data1: String?,
        val data2: String?,
        val data3: String?
    )
}
