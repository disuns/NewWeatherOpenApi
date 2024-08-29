package com.project.newweatheropenapi.network.dataclass.request.navermap

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class NaverMapRequest (
    val request: String,
    val coords: String,
    val sourcecrs: String,
    val targetcrs: String,
    val output: String,
    val orders: String,
    val apikeyid: String,
    val apikey: String
)

fun NaverMapRequest.toMap():Map<String, String>{
    val utf8Charset = StandardCharsets.UTF_8
    fun encode(value: String): String = URLEncoder.encode(value, utf8Charset.name())

    return mapOf(
        "request" to request,
        "coords" to encode(coords),
        "sourcecrs" to encode(sourcecrs),
        "targetcrs" to encode(targetcrs),
        "output" to output,
        "orders" to encode(orders),
        "X-NCP-APIGW-API-KEY-ID" to apikeyid,
        "X-NCP-APIGW-API-KEY" to apikey
    )
}