package com.project.newweatheropenapi.network.dataclass.request.datapotal

data class StationFindRequest(
    val serviceKey: String,
    val returnType: String,
    val tmX: String,
    val tmY: String,
    val ver: String
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
