package com.android.sj.domain.models

data class WeekRainSkyData(
    val items : MutableList<Item>
) {
    data class Item(
        val rnStAm: MutableList<Int>,
        val rnStPm: MutableList<Int>,
        val wfAm: MutableList<String>,
        val wfPm: MutableList<String>
    )
}
