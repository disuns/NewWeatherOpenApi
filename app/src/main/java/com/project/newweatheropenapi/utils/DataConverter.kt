package com.project.newweatheropenapi.utils

import android.content.Context
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
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

@Singleton
class DataConverter @Inject constructor(@ApplicationContext private val context: Context) {
    //이미지+날씨정보

//    fun rainPerConvert(code: String)=  application.getString(R.string.perUnit, code)
//
//
//
//
//
//    fun wetConvert(code: String)= application.getString(R.string.perUnit, code)
//
//    fun nowRainConvert(code: String)= application.getString(R.string.nowRainUnit, code)
//
//
//

//
//
//
//    fun windPower(code: String) = application.getString(R.string.windUnit, code)
//

//

//

//

//
//    fun timeDataConvert(code: String) = application.getString(R.string.time, code.substring(NUM0.toInt(),NUM2.toInt()))
//
//    fun dateConvert(code : String) : String{
//        val splitString = code.chunked(NUM2.toInt())
//        return application.getString(R.string.date,splitString[NUM2.toInt()],splitString[NUM3.toInt()])
//    }

    fun mapAddressConvert(reverseGeocoder: NaverMapResponse): String {
        with(reverseGeocoder) {
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

    fun landCodeGu(address: String): String {
        val landArray = context.resources.getStringArray(R.array.land)
        val codeArray = context.resources.getStringArray(R.array.landCode)

        if (address.contains(landArray[0]) || address.contains(landArray[1]) || address.contains(
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
                address.contains(it)
            }
        }?.second ?: codeArray[8]
    }

//    fun weekDateConvert(weekDate: WeekDate) = application.getString(R.string.weekDate, weekDate.month,weekDate.day,weekDate.dayOfWeek)


//    fun rltmGradeConvert(grade:String) : String{
//        with(application){
//            val gradeString = when(grade){
//                NUM1->{getString(R.string.grade1)}
//                NUM2->{getString(R.string.grade2)}
//                NUM3->{getString(R.string.grade3)}
//                else->{getString(R.string.grade4)}
//            }
//
//            return getString(R.string.grade, gradeString)
//        }
//    }

//    fun rltmValueConvert(rltm:Int, value : String): String {
//        with(application){
//            val rltmChange = when(rltm){
//                NUM0.toInt()->{value}
//                NUM1.toInt(), NUM2.toInt()->{getString(R.string.rltmUnit1, value)}
//                else->{getString(R.string.rltmUnit2, value)}
//            }
//
//            return getString(R.string.concentration,rltmChange)
//        }
//    }
//
//    fun rltmTitle(value : String) = application.getString(R.string.rltmStation, value)
//
//    fun rltmStationDate(date : String) = application.getString(R.string.stationTime, date)
//
//    fun rltmFlag(flag : String) = application.getString(R.string.flag, flag)
//
//    fun airDateAndCode(date : String, code: String) = application.getString(R.string.dateAndCode, date, code)
//
//    fun airInformGrade(informGrade: String) = informGrade.split(",")
//
//    fun actionKnact(actionKnack : String) = application.getString(R.string.actionKnack, actionKnack)

    fun convertGRIDGPS(mode: Int, latLng: LatLng): LatLng {

        val conLat = latLng.latitude
        val conLon = latLng.longitude

        val RE = 6371.00877 // 지구 반경(km)
        val GRID = 5.0 // 격자 간격(km)
        val SLAT1 = 30.0 // 투영 위도1(degree)
        val SLAT2 = 60.0 // 투영 위도2(degree)
        val OLON = 126.0 // 기준점 경도(degree)
        val OLAT = 38.0 // 기준점 위도(degree)
        val XO = 43.0 // 기준점 X좌표(GRID)
        val YO = 136.0 // 기1준점 Y좌표(GRID)

        //
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        //
        val DEGRAD = Math.PI / 180.0
        val RADDEG = 180.0 / Math.PI
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD
        var sn =
            ln(cos(slat1) / cos(slat2)) / ln(tan(Math.PI * 0.25 + slat2 * 0.5) / tan(Math.PI * 0.25 + slat1 * 0.5))
        val sf = tan(Math.PI * 0.25 + slat1 * 0.5).pow(sn) * cos(slat1) / sn
        val ro = re * sf / tan(Math.PI * 0.25 + olat * 0.5).pow(sn)

        return if (mode == 0) {
            val ra = re * sf / (tan(Math.PI * 0.25 + conLat * DEGRAD * 0.5)).pow(sn)
            var theta = conLon * DEGRAD - olon
            theta = when {
                theta > Math.PI -> theta - 2.0 * Math.PI
                theta < -Math.PI -> theta + 2.0 * Math.PI
                else -> theta
            }
            theta *= sn

            LatLng(floor(ra * sin(theta) + XO + 0.5), floor(ro - ra * cos(theta) + YO + 0.5))
        } else {
            val xn = conLat - XO
            val yn = ro - conLon + YO
            var ra = sqrt(xn * xn + yn * yn)
            if (sn < 0.0)   ra = -ra
            var alat = (re * sf / ra).pow(1.0 / sn)
            alat = 2.0 * atan(alat) - Math.PI * 0.5
            val theta = when{
                abs(xn) <= 0.0 -> 0.0
                abs(yn) <= 0.0 ->Math.PI * 0.5 * (if (xn < 0.0) -1 else 1)
                else -> atan2(xn, yn)
            }
            val alon = theta / sn + olon
            LatLng(alat * RADDEG, alon * RADDEG)
        }
    }
}