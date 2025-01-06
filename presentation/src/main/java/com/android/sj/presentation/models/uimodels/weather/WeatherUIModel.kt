package com.android.sj.presentation.models.uimodels.weather

import android.graphics.drawable.Drawable
import com.android.sj.presentation.enum.WeatherImgEnum

data class WeatherUIModel(
    var nowTemp: String = "",
    var nowRain: String = "",
    var nowWet: String = "",
    var nowWind: String = "",
    var weatherImg: WeatherImgEnum = WeatherImgEnum.None,
    var weatherImgDrawable: Drawable? = null,
    var weatherText: String = "",
    var windDir: String = ""
)
