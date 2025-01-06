package com.android.sj.data.network.request.datapotal

import com.android.sj.data.network.ApiConstants.DATA_POTAL_SERVICE_KEY
import com.android.sj.data.network.ApiConstants.DATA_TYPE_UPPER
import com.android.sj.data.network.ApiConstants.NUM_OF_ROWS_DEFAULT
import com.android.sj.data.network.ApiConstants.PAGE_NO_DEFAULT

data class WeatherRequest(
    val serviceKey: String = DATA_POTAL_SERVICE_KEY,
    val pageNo:String = PAGE_NO_DEFAULT,
    val numOfRows:String = NUM_OF_ROWS_DEFAULT,
    val dataType:String = DATA_TYPE_UPPER,
    val baseDate:String,
    val baseTime:String,
    val nx:String,
    val ny:String
)

fun WeatherRequest.toMap():Map<String, String>{
    return mapOf(
        "serviceKey" to serviceKey,
        "pageNo" to pageNo,
        "numOfRows" to numOfRows,
        "dataType" to dataType,
        "base_date" to baseDate,
        "base_time" to baseTime,
        "nx" to nx,
        "ny" to ny
    )
}