package com.android.sj.presentation.common.models.uimodels.weather

import android.graphics.drawable.Drawable

data class TimeWeatherUIModel(
    val items: MutableList<Item>
) {
    data class Item(
        var weatherDate: String = "",
        var weatherTime: String = "",
        var temp: String = "",
        var rainPer: String = "",
        var rainMm: String = "",
        var wet: String = "",
        var windDir: String = "",
        var windPower: String = "",
        var imgDrawable : Drawable? = null
    )
}
