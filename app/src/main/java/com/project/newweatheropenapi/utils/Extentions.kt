package com.project.newweatheropenapi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.orhanobut.logger.Logger
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.dataclass.WeekDate
import com.project.newweatheropenapi.enum.WeatherImgEnum
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

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


fun NaverMapResponse.mapAddressConvert(context: Context): String {
    with(this) {
        return if (this.status.code == 0) {
            with(results[results.lastIndex]) {
                when (name) {
                    ROAD_ADDR -> {
                        "${region.area1.name} ${region.area2.name} ${land.name} ${land.number1}"
                    }

                    ADDR -> {
                        "${region.area1.name} ${region.area2.name} ${region.area3.name} ${land.number1}"
                    }

                    else -> {
                        context.getString(R.string.loadingImage)
                    }
//                        "R.string.nullString"
                }
            }
        } else {
            context.getString(R.string.loadingImage)
//                R.string.UNKNOWN_ADDRESS
        }
    }
}

fun String.landCodeGu(context: Context): String {
    val landArray = context.resources.getStringArray(R.array.land)
    val codeArray = context.resources.getStringArray(R.array.landCode)

    if (this.contains(landArray[0]) || this.contains(landArray[1]) || this.contains(
            landArray[2]
        )
    ) {
        codeArray[0]
    }
    val landCodeGroups = listOf(
        landArray.slice(0..2) to codeArray[0],
        listOf(landArray[3]) to codeArray[1],
        landArray.slice(4..6) to codeArray[2],
        listOf(landArray[7]) to codeArray[3],
        landArray.slice(8..9) to codeArray[4],
        listOf(landArray[10]) to codeArray[5],
        landArray.slice(11..12) to codeArray[6],
        landArray.slice(13..15) to codeArray[7]
    )

    return landCodeGroups.find { (lands, _) ->
        lands.any {
            this.contains(it)
        }
    }?.second ?: codeArray[8]
}

fun String.dataPotalResultCode(context: Context): String {
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
    return context.getString(codeToString)
}

fun String.tempConvert(context: Context) = context.getString(R.string.tempUnit, this)
fun String.nowRainConvert(context: Context) = context.getString(R.string.nowRainUnit, this)
fun String.rainConvert(context: Context) = context.getString(R.string.rainUnit, this)
fun String.nowWetConvert(context: Context) = context.getString(R.string.nowWetUnit, this)
fun String.windPower(context: Context, dir: String) =
    context.getString(R.string.nowWindUnit, dir, this)

fun String.windPower(context: Context) =
    context.getString(R.string.windUnit, this)

fun String.timeDataConvert(context: Context) =
    context.getString(R.string.time, this.substring(0, 2))

fun String.dateConvert(context: Context): String {
    val splitString = this.chunked(2)
    return context.getString(R.string.date, splitString[2], splitString[3])
}

fun String.wetConvert(context: Context) = context.getString(R.string.perUnit, this)

fun String.rainPerConvert(context: Context) = context.getString(R.string.perUnit, this)

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

fun LatLng.convertGRIDGPS(mode: Int): LatLng {
    val latitude = this.latitude
    val longitude = this.longitude

    val earthRadius = 6371.00877 // 지구 반경(km)
    val gridSpacing = 5.0 // 격자 간격(km)
    val standardLatitude1 = 30.0 // 투영 위도1(degree)
    val standardLatitude2 = 60.0 // 투영 위도2(degree)
    val referenceLongitude = 126.0 // 기준점 경도(degree)
    val referenceLatitude = 38.0 // 기준점 위도(degree)
    val originX = 43.0 // 기준점 X좌표(GRID)
    val originY = 136.0 // 기준점 Y좌표(GRID)

    val degreeToRad = Math.PI / 180.0
    val radToDegree = 180.0 / Math.PI
    val re = earthRadius / gridSpacing
    val slat1 = standardLatitude1 * degreeToRad
    val slat2 = standardLatitude2 * degreeToRad
    val olon = referenceLongitude * degreeToRad
    val olat = referenceLatitude * degreeToRad
    val sn = ln(cos(slat1) / cos(slat2)) / ln(tan(Math.PI * 0.25 + slat2 * 0.5) / tan(Math.PI * 0.25 + slat1 * 0.5))
    val sf = tan(Math.PI * 0.25 + slat1 * 0.5).pow(sn) * cos(slat1) / sn
    val ro = re * sf / tan(Math.PI * 0.25 + olat * 0.5).pow(sn)

    return if (mode == 0) {
        val ra = re * sf / (tan(Math.PI * 0.25 + latitude * degreeToRad * 0.5)).pow(sn)
        var theta = longitude * degreeToRad - olon
        theta = when {
            theta > Math.PI -> theta - 2.0 * Math.PI
            theta < -Math.PI -> theta + 2.0 * Math.PI
            else -> theta
        }
        theta *= sn

        LatLng(floor(ra * sin(theta) + originX + 0.5), floor(ro - ra * cos(theta) + originY + 0.5))
    } else {
        val xn = latitude - originX
        val yn = ro - longitude + originY
        var ra = sqrt(xn * xn + yn * yn)
        if (sn < 0.0) ra = -ra
        var alat = (re * sf / ra).pow(1.0 / sn)
        alat = 2.0 * atan(alat) - Math.PI * 0.5
        val theta = when {
            abs(xn) <= 0.0 -> 0.0
            abs(yn) <= 0.0 -> Math.PI * 0.5 * (if (xn < 0.0) -1 else 1)
            else -> atan2(xn, yn)
        }
        val alon = theta / sn + olon
        LatLng(alat * radToDegree, alon * radToDegree)
    }
}


fun Dp.sp() = this.value.sp

fun WeekDate.weekDateConvert(context: Context) =
    context.getString(R.string.weekDate, this.month, this.day, this.dayOfWeek)

fun String.rltmTitle(context: Context) = context.getString(R.string.rltmStation, this)

fun String.rltmStationDate(context: Context) = context.getString(R.string.stationTime, this)


fun String.rltmValueConvert(rltm: Int, context: Context): String {
    if (this == "-") {
        return context.getString(R.string.concentration, this)
    }

    val rltmChange = when (rltm) {
        0 -> this
        1, 2 -> context.getString(R.string.rltmUnit1, this)
        else -> context.getString(R.string.rltmUnit2, this)
    }
    return context.getString(R.string.concentration, rltmChange)
}

fun String.rltmGradeConvert(context: Context): String {
    if (this == "-") {
        return context.getString(R.string.grade, this)
    }
    val gradeArray = context.resources.getStringArray(R.array.grade)
    return context.getString(R.string.grade, gradeArray[this.toInt()-1])
}

fun String.rltmFlag(context: Context) = context.getString(R.string.flag, this)

fun logMessage(message: Any?, tag: String = "MyApp") {
    Logger.t(tag).e(message.toString())
}
fun toastMessage(text:String, context: Context) = Toast.makeText(context,text, Toast.LENGTH_SHORT).show()

fun String.airDateAndCode(date : String, context: Context) = context.getString(R.string.dateAndCode, date, this)

fun String.airInformGrade() = this.split(",")

fun String.actionKnact(context: Context) = context.getString(R.string.actionKnack, this)

@Composable
fun Modifier.splashShimmerEffect() : Modifier{
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    return this.background(brush)
}