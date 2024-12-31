package com.android.sj.presentation.models.request.navermap

import com.android.sj.presentation.RequestConstants.DATA_TYPE_LOWER
import com.android.sj.presentation.RequestConstants.MAP_CLIENT_KEY
import com.android.sj.presentation.RequestConstants.MAP_CLIENT_KEY_ID
import com.android.sj.presentation.RequestConstants.MAP_COORDINATE_DEFAULT
import com.android.sj.presentation.RequestConstants.MAP_COORDINATE_TM
import com.android.sj.presentation.RequestConstants.MAP_ORDERS
import com.android.sj.presentation.RequestConstants.MAP_REQUEST_DEFAULT

data class NaverMapRequest (
    val request: String = MAP_REQUEST_DEFAULT,
    val coords: String,
    val sourcecrs: String = MAP_COORDINATE_DEFAULT,
    val targetcrs: String = MAP_COORDINATE_TM,
    val output: String = DATA_TYPE_LOWER,
    val orders: String = MAP_ORDERS,
    val apikeyid:String = MAP_CLIENT_KEY_ID,
    val apikey:String = MAP_CLIENT_KEY
)

fun NaverMapRequest.toMap():Map<String, String>{
    return mapOf(
        "request" to request,
        "coords" to coords,
        "sourcecrs" to sourcecrs,
        "targetcrs" to targetcrs,
        "output" to output,
        "orders" to orders,
        "X-NCP-APIGW-API-KEY-ID" to apikeyid,
        "X-NCP-APIGW-API-KEY" to apikey
    )
}