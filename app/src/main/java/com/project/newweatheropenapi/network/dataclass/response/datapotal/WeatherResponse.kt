package com.project.newweatheropenapi.network.dataclass.response.datapotal

data class WeatherResponse(
    val response: Response
) {
    data class Response(
        val header: Header,
        val body: Body
    ) {
        data class Header(
            val resultCode: String,
            val resultMsg: String
        )

        data class Body(
            val dataType: String,
            val items: Items,
            val numOfRows: Int,
            val pageNo: Int,
            val totalCount: Int
        ) {
            data class Items(
                val item: MutableList<Item>
            ) {
                data class Item(
                    val baseDate: String,
                    val baseTime: String,
                    val category: String,
                    val nx: Int,
                    val ny: Int,
                    val fcstDate: String,
                    val fcstTime: String,
                    val fcstValue: String
                )
            }
        }
    }
}
