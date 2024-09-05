package com.project.newweatheropenapi.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.app.WeatherApplication
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataConverter @Inject constructor(private val application: WeatherApplication){

//    fun dataPotalResultCode(code: String) {
//
//        val errorMsg: String = with(application) {
//            when (code) {
//                APPLICATION_ERROR -> {
//                    getString(R.string.APPLICATION_ERROR)
//                }
//                DB_ERROR -> {
//                    getString(R.string.DB_ERROR)
//                }
//                NODATA_ERROR -> {
//                    getString(R.string.NODATA_ERROR)
//                }
//                HTTP_ERROR -> {
//                    getString(R.string.HTTP_ERROR)
//                }
//                SERVICETIME_OUT -> {
//                    getString(R.string.SERVICETIME_OUT)
//                }
//                INVALID_REQUEST_PARAMETER_ERROR -> {
//                    getString(R.string.INVALID_REQUEST_PARAMETER_ERROR)
//                }
//                NO_MANDATORY_REQUEST_PARAMETERS_ERROR -> {
//                    getString(R.string.NO_MANDATORY_REQUEST_PARAMETERS_ERROR)
//                }
//                NO_OPENAPI_SERVICE_ERROR -> {
//                    getString(R.string.NO_OPENAPI_SERVICE_ERROR)
//                }
//                SERVICE_ACCESS_DENIED_ERROR -> {
//                    getString(R.string.SERVICE_ACCESS_DENIED_ERROR)
//                }
//                TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR -> {
//                    getString(R.string.TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR)
//                }
//                LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR -> {
//                    getString(R.string.LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR)
//                }
//                SERVICE_KEY_IS_NOT_REGISTERED_ERROR -> {
//                    getString(R.string.SERVICE_KEY_IS_NOT_REGISTERED_ERROR)
//                }
//                DEADLINE_HAS_EXPIRED_ERROR -> {
//                    getString(R.string.DEADLINE_HAS_EXPIRED_ERROR)
//                }
//                UNREGISTERED_IP_ERROR -> {
//                    getString(R.string.UNREGISTERED_IP_ERROR)
//                }
//                UNSIGNED_CALL_ERROR -> {
//                    getString(R.string.UNSIGNED_CALL_ERROR)
//                }
//                else -> {
//                    getString(R.string.UNKNOWN_ERROR)
//                }
//            }
//        }
//
////        Log.e("convert", errorMsg)
//        weatherApplication.toastMessage(errorMsg)
//    }
    //이미지+날씨정보

//    fun rainPerConvert(code: String)=  application.getString(R.string.perUnit, code)
//
//    fun tempConvert(code: String)=  application.getString(R.string.tempUnit, code)
//
//    fun nowWetConvert(code: String)= application.getString(R.string.nowWetUnit, code)
//
//    fun wetConvert(code: String)= application.getString(R.string.perUnit, code)
//
//    fun nowRainConvert(code: String)= application.getString(R.string.nowRainUnit, code)
//
//    fun rainConvert(code: String)= application.getString(R.string.rainUnit, code)
//
//    fun windDir(code: String): String {
//        return with(application) {
//            when (((code.toInt() + 22.5 * 0.5) / 22.5).toInt()) {
//                NUM1.toInt() -> getString(R.string.NNE)
//                NUM2.toInt() -> getString(R.string.NE)
//                NUM3.toInt() -> getString(R.string.ENE)
//                NUM4.toInt() -> getString(R.string.E)
//                NUM5.toInt() -> getString(R.string.ESE)
//                NUM6.toInt() -> getString(R.string.SE)
//                NUM7.toInt() -> getString(R.string.SSE)
//                NUM8.toInt() -> getString(R.string.S)
//                NUM9.toInt() -> getString(R.string.SSW)
//                NUM10.toInt() -> getString(R.string.SW)
//                NUM11.toInt() -> getString(R.string.WSW)
//                NUM12.toInt() -> getString(R.string.W)
//                NUM13.toInt() -> getString(R.string.WNW)
//                NUM14.toInt() -> getString(R.string.NW)
//                NUM15.toInt() -> getString(R.string.NNW)
//                else -> getString(R.string.N)
//            }
//        }
//    }
//
//    fun windPower(dir: String, code: String)= application.getString(R.string.nowWindUnit, dir, code)
//
//    fun windPower(code: String) = application.getString(R.string.windUnit, code)
//
//    fun fcstRainImgConvert(code: String): FcstImgEnum {
//        return when (code) {
//            NUM1 -> {
//                FcstImgEnum.Rain
//            }
//            NUM2 -> {
//                FcstImgEnum.Rain
//            }//비+눈
//            NUM3 -> {
//                FcstImgEnum.Snow
//            }
//            NUM4 -> {
//                FcstImgEnum.Rain
//            }
//            NUM5 -> {
//                FcstImgEnum.Rain
//            }
//            NUM6 -> {
//                FcstImgEnum.Rain
//            }//비+눈
//            NUM7 -> {
//                FcstImgEnum.Snow
//            }
//            else -> {
//                FcstImgEnum.Sun
//            }
//        }
//    }
//
//    @SuppressLint("UseCompatLoadingForDrawables")
//    fun fcstImgConvert(enumFcstImgEnum: FcstImgEnum): Drawable? {
//        return with(application) {
//            when (enumFcstImgEnum) {
//                FcstImgEnum.ClodeSun -> { getDrawable(R.drawable.cloudsun) }
//                FcstImgEnum.Cloude -> { getDrawable(R.drawable.cloud) }
//                FcstImgEnum.Rain -> { getDrawable(R.drawable.rain) }
//                FcstImgEnum.Snow -> { getDrawable(R.drawable.snow) }
//                else -> { getDrawable(R.drawable.sunny) }
//            }
//        }
//    }
//
//    fun skyConvert(code: String): String {
//        return with(application){
//            when (code) {
//                NUM3 -> { getString(R.string.manycloud) }
//                NUM4 -> { getString(R.string.cloud) }
//                else -> { getString(R.string.sun) }
//            }
//        }
//
//    }
//
//    fun skyImgEnum(code: String, fcstImgEnum: FcstImgEnum): FcstImgEnum {
//        val fcstImg = if (fcstImgEnum == FcstImgEnum.Sun) {
//            when (code) {
//                NUM3 -> { FcstImgEnum.ClodeSun }
//                NUM4 -> { FcstImgEnum.Cloude }
//                else -> { FcstImgEnum.Sun }
//            }
//        }
//        else fcstImgEnum
//        return fcstImg
//    }
//
//    fun timeDataConvert(code: String) = application.getString(R.string.time, code.substring(NUM0.toInt(),NUM2.toInt()))
//
//    fun dateConvert(code : String) : String{
//        val splitString = code.chunked(NUM2.toInt())
//        return application.getString(R.string.date,splitString[NUM2.toInt()],splitString[NUM3.toInt()])
//    }

    fun mapAddressConvert(reverseGeocoder: NaverMapResponse):String{
//        with(reverseGeocoder){
//            return if(this.status.code == 0) {
//                with(results[results.lastIndex]){
//                    when(name){
//                        ROAD_ADDR->{"${region.area1.name} ${region.area2.name} ${land.name} ${land.number1}"}
//                        ADDR->{"${region.area1.name} ${region.area2.name} ${region.area3.name} ${land.number1}"}
//                        else->{application.getString(R.string.nullString)}
//                    }
//                }
//            }
//            else{
//                application.getString(R.string.UNKNOWN_ADDRESS)
//            }
//        }
        return "바꾸는거 테스트"
    }

//    fun landCodeGu(land:String):String{
//        return with(application) {
//            when(land){
//                getString(R.string.land1), getString(R.string.land2), getString(R.string.land3)->{getString(R.string.landCode1)}
//                getString(R.string.land4)->{getString(R.string.landCode2)}
//                getString(R.string.land5), getString(R.string.land6), getString(R.string.land7)->{getString(R.string.landCode3)}
//                getString(R.string.land8)->{getString(R.string.landCode4)}
//                getString(R.string.land9),getString(R.string.land10)->{getString(R.string.landCode5)}
//                getString(R.string.land11)->{getString(R.string.landCode6)}
//                getString(R.string.land12), getString(R.string.land13)->{getString(R.string.landCode7)}
//                getString(R.string.land14),getString(R.string.land15),getString(R.string.land16)->{getString(R.string.landCode8)}
//                else->{getString(R.string.landCode9)}
//            }
//        }
//    }

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

}