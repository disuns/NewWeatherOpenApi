package com.android.sj.presentation.common.enum

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.android.sj.presentation.R

enum class WeatherImgEnum {
    None,
    Sun,
    Cloud,
    Rain,
    Snow,
    CloudSun
}

fun WeatherImgEnum.imgConvert(context: Context): Drawable? {
    val drawableRes = when (this) {
        WeatherImgEnum.CloudSun ->  R.drawable.cloudsun
        WeatherImgEnum.Cloud ->  R.drawable.cloud
        WeatherImgEnum.Rain ->  R.drawable.rain
        WeatherImgEnum.Snow ->  R.drawable.snow
        else ->  R.drawable.sunny
    }

    return ContextCompat.getDrawable(context,drawableRes)
}