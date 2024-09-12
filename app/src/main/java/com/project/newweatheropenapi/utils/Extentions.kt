package com.project.newweatheropenapi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.orhanobut.logger.Logger
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.enum.WeatherImgEnum

fun Context.isNetworkCheck(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
    val nw = cm.activeNetwork ?: return false
    val networkCapabilities = cm.getNetworkCapabilities(nw) ?: return false
    return when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

fun String.dataPotalResultCode(context: Context) {
    val codeToString = when (this) {
        APPLICATION_ERROR -> R.string.APPLICATION_ERROR
        DB_ERROR -> R.string.DB_ERROR
        NODATA_ERROR -> R.string.NODATA_ERROR
        HTTP_ERROR -> R.string.HTTP_ERROR
        SERVICETIME_OUT -> R.string.SERVICETIME_OUT
        INVALID_REQUEST_PARAMETER_ERROR -> R.string.INVALID_REQUEST_PARAMETER_ERROR
        NO_MANDATORY_REQUEST_PARAMETERS_ERROR -> R.string.NO_MANDATORY_REQUEST_PARAMETERS_ERROR
        NO_OPENAPI_SERVICE_ERROR -> R.string.NO_OPENAPI_SERVICE_ERROR
        SERVICE_ACCESS_DENIED_ERROR -> R.string.SERVICE_ACCESS_DENIED_ERROR
        TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR -> R.string.TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR
        LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR -> R.string.LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR
        SERVICE_KEY_IS_NOT_REGISTERED_ERROR -> R.string.SERVICE_KEY_IS_NOT_REGISTERED_ERROR
        DEADLINE_HAS_EXPIRED_ERROR -> R.string.DEADLINE_HAS_EXPIRED_ERROR
        UNREGISTERED_IP_ERROR -> R.string.UNREGISTERED_IP_ERROR
        UNSIGNED_CALL_ERROR -> R.string.UNSIGNED_CALL_ERROR
        else -> R.string.UNKNOWN_ERROR
    }
    logMessage(context.getString(codeToString))
}

fun String.tempConvert(context: Context) = context.getString(R.string.tempUnit, this)
fun String.rainConvert(context: Context) = context.getString(R.string.rainUnit, this)
fun String.nowWetConvert(context: Context) = context.getString(R.string.nowWetUnit, this)
fun String.windPower(context: Context, dir: String) =
    context.getString(R.string.nowWindUnit, dir, this)

fun String.windDir(context: Context): String {
    val dir = when (((this.toInt() + 22.5 * 0.5) / 22.5).toInt()) {
        1 -> R.string.NNE
        2 -> R.string.NE
        3 -> R.string.ENE
        4 -> R.string.E
        5 -> R.string.ESE
        6 -> R.string.SE
        7 -> R.string.SSE
        8 -> R.string.S
        9 -> R.string.SSW
        10 -> R.string.SW
        11 -> R.string.WSW
        12 -> R.string.W
        13 -> R.string.WNW
        14 -> R.string.NW
        15 -> R.string.NNW
        else -> R.string.N
    }
    return context.getString(dir)
}

fun String.weatherRainImgConvert(): WeatherImgEnum {
    return when (this) {
        "1", "2", "4", "5", "6" -> WeatherImgEnum.Rain
        "3", "7" -> WeatherImgEnum.Snow
        else -> WeatherImgEnum.Sun
    }
}

fun String.skyImgEnum(weatherImgEnum: WeatherImgEnum): WeatherImgEnum {
    return if (weatherImgEnum == WeatherImgEnum.Sun) {
        when (this) {
            "3" -> WeatherImgEnum.CloudSun
            "4" -> WeatherImgEnum.Cloud
            else -> WeatherImgEnum.Sun
        }
    } else weatherImgEnum
}

fun String.skyConvert(context: Context): String {
    val stringSky = when (this) {
        "3" -> R.string.manycloud
        "4" -> R.string.cloud
        else -> R.string.sun
    }
    return context.getString(stringSky)
}

fun logMessage(message: Any?, tag: String = "MyApp") {
    Logger.t(tag).e(message.toString())
}