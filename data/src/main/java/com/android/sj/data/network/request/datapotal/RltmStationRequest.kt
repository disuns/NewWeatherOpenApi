package com.android.sj.data.network.request.datapotal

import com.android.sj.data.network.ApiConstants.DATA_POTAL_SERVICE_KEY
import com.android.sj.data.network.ApiConstants.DATA_TYPE_LOWER
import com.android.sj.data.network.ApiConstants.DATE_TERM
import com.android.sj.data.network.ApiConstants.NUM_OF_ROWS_AIR
import com.android.sj.data.network.ApiConstants.PAGE_NO_DEFAULT
import com.android.sj.data.network.ApiConstants.RLTM_DATA_VERSION

data class RltmStationRequest(
    val serviceKey: String = DATA_POTAL_SERVICE_KEY,
    val returnType: String = DATA_TYPE_LOWER,
    val pageNo: String = PAGE_NO_DEFAULT,
    val numOfRows: String = NUM_OF_ROWS_AIR,
    val stationName: String,
    val dataTerm: String = DATE_TERM,
    val ver: String = RLTM_DATA_VERSION
)

fun RltmStationRequest.toMap(): Map<String, String> {
    return mapOf(
        "serviceKey" to serviceKey,
        "returnType" to returnType,
        "pageNo" to pageNo,
        "numOfRows" to numOfRows,
        "stationName" to stationName,
        "dataTerm" to dataTerm,
        "ver" to ver
    )
}
