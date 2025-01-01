package com.android.sj.presentation.models.uimodels.weather

data class WeekRainSkyUIData(
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
