package com.project.newweatheropenapi.network.dataclass.response.datapotal

data class AirQualityResponse(
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
                val actionKnack: String?,
                val dataTime: String,
                val imageUrl1: String,
                val imageUrl2: String,
                val imageUrl3: String,
                val imageUrl4: String,
                val imageUrl5: String,
                val imageUrl6: String,
                val imageUrl7: String,
                val imageUrl8: String,
                val imageUrl9: String,
                val informCause: String,
                val informCode: String,
                val informData: String,
                val informGrade: String,
                val informOverall: String
            )
        }
    }
}