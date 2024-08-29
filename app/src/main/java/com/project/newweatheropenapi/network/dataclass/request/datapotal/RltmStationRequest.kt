package com.project.newweatheropenapi.network.dataclass.request.datapotal

data class RltmStationRequest(
    val serviceKey: String,
    val returnType: String,
    val pageNo: String,
    val numOfRows: String,
    val stationName: String,
    val dataTerm: String,
    val ver: String
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
