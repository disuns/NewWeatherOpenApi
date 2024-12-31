package com.android.sj.domain.models

data class WeatherData (
    val items : MutableList<Item>
){
    data class Item(
        val category : String,
        val value : String
    )
}