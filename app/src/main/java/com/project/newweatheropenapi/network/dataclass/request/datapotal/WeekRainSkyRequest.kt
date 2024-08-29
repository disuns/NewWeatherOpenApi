package com.project.newweatheropenapi.network.dataclass.request.datapotal

data class WeekRainSkyRequest(
    val serviceKey: String,
    val pageNo:String,
    val numOfRows:String,
    val dataType:String,
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