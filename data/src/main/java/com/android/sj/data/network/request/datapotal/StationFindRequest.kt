package com.android.sj.data.network.request.datapotal

import com.android.sj.data.network.ApiConstants.DATA_POTAL_SERVICE_KEY
import com.android.sj.data.network.ApiConstants.DATA_TYPE_LOWER
import com.android.sj.data.network.ApiConstants.STATION_VERSION

data class StationFindRequest(
    val serviceKey: String = DATA_POTAL_SERVICE_KEY,
    val returnType: String = DATA_TYPE_LOWER,
    val tmX: String,
    val tmY: String,
    val ver: String = STATION_VERSION
)

fun StationFindRequest.toMap(): Map<String, String> {
    return mapOf(
        "serviceKey" to serviceKey,
        "returnType" to returnType,
        "tmX" to tmX,
        "tmY" to tmY,
        "ver" to ver
    )
}
