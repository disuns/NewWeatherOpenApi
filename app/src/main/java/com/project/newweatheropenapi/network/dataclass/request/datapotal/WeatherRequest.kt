package com.project.newweatheropenapi.network.dataclass.request.datapotal

data class WeatherRequest(
    val serviceKey: String,
    val pageNo:String,
    val numOfRows:String,
    val dataType:String,
    val base_date:String,
    val base_time:String,
    val nx:String,
    val ny:String
)

fun WeatherRequest.toMap():Map<String, String>{
    return mapOf(
        "serviceKey" to serviceKey,
        "pageNo" to pageNo,
        "numOfRows" to numOfRows,
        "dataType" to dataType,
        "base_date" to base_date,
        "base_time" to base_time,
        "nx" to nx,
        "ny" to ny
    )
}