package com.project.newweatheropenapi.network.dataclass.response.datapotal

data class StationFindResponse(
    val response: Response
){
    data class Response(
        val body: Body,
        val header: Header
    ){
        data class Header(
            val resultCode: String,
            val resultMsg: String
        )

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
