package com.android.sj.data.network.request.datapotal

import com.android.sj.data.network.ApiConstants.AIR_CODE
import com.android.sj.data.network.ApiConstants.DATA_POTAL_SERVICE_KEY
import com.android.sj.data.network.ApiConstants.DATA_TYPE_LOWER
import com.android.sj.data.network.ApiConstants.NUM_OF_ROWS_AIR
import com.android.sj.data.network.ApiConstants.PAGE_NO_DEFAULT

data class AirQualityRequest(
    val serviceKey: String = DATA_POTAL_SERVICE_KEY,
    val returnType: String = DATA_TYPE_LOWER,
    val pageNo: String = PAGE_NO_DEFAULT,
    val numOfRows: String = NUM_OF_ROWS_AIR,
    val searchDate: String,
    val informCode: String = AIR_CODE
)

fun AirQualityRequest.toMap(): Map<String, String> {
    return mapOf(
        "serviceKey" to serviceKey,
        "returnType" to returnType,
        "pageNo" to pageNo,
        "numOfRows" to numOfRows,
        "searchDate" to searchDate,
        "InformCode" to informCode
    )
}