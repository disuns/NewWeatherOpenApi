package com.android.sj.presentation.common.models.uimodels.weather

data class WeekRainSkyUIModel(
    val items : MutableList<Item>
){
    data class Item(
        var weekDate: String = "",
        var rainAm:String = "",
        var rainPm:String = "",
        var skyAm:String? = null,
        var skyPm:String? = null
    )
}
