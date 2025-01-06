package com.android.sj.data.network.request.datapotal

import com.android.sj.data.network.ApiConstants.DATA_POTAL_SERVICE_KEY
import com.android.sj.data.network.ApiConstants.DATA_TYPE_UPPER
import com.android.sj.data.network.ApiConstants.NUM_OF_ROWS_WEEK
import com.android.sj.data.network.ApiConstants.PAGE_NO_DEFAULT

data class WeekRainSkyRequest(
    val serviceKey: String = DATA_POTAL_SERVICE_KEY,
    val pageNo:String = PAGE_NO_DEFAULT,
    val numOfRows:String = NUM_OF_ROWS_WEEK,
    val dataType:String = DATA_TYPE_UPPER,
    val regId:String,
    val tmFc:String
)

fun WeekRainSkyRequest.toMap(): Map<String, String> {
    return mapOf(
        "serviceKey" to serviceKey,
        "pageNo" to pageNo,
        "numOfRows" to numOfRows,
        "dataType" to dataType,
        "regId" to regId,
        "tmFc" to tmFc
    )
}