package com.project.newweatheropenapi.network.dataclass.response.datapotal

import com.sjchoi.weather.dataclass.datapotal.Header

data class StationFindResponse(
    val response: Response
){
    data class Response(
        val body: Body,
        val header: Header
    ){
        data class Body(
            val items: MutableList<Item>,
            val numOfRows: Int,
            val pageNo: Int,
            val totalCount: Int
        ){
            data class Item(
                val tm : Double,
                val addr : String,
                val stationName : String
            )
        }
    }
}
