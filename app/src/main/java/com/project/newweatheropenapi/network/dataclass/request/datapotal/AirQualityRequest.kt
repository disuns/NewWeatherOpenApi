package com.project.newweatheropenapi.network.dataclass.request.datapotal

data class AirQualityRequest(
    val serviceKey: String,
    val returnType: String,
    val pageNo: String,
    val numOfRows: String,
    val searchDate: String,
    val InformCode: String
)

fun AirQualityRequest.toMap(): Map<String, String> {
    return mapOf(
        "serviceKey" to serviceKey,
        "returnType" to returnType,
        "pageNo" to pageNo,
        "numOfRows" to numOfRows,
        "searchDate" to searchDate,
        "InformCode" to InformCode
    )
}