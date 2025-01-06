package com.android.sj.domain.models

data class TimeWeatherData(
    val items: MutableList<Item>
) {
    data class Item(
        var weatherDate: String = "",
        var weatherTime: String = "",
        var category: String = "",
        var value: String = ""
    )
}