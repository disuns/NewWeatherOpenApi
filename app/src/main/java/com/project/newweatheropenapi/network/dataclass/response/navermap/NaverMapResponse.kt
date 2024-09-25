package com.project.newweatheropenapi.network.dataclass.response.navermap

data class NaverMapResponse(
    val results: List<Result>,
    val status: Status
) {
    data class Status(
        val code: Int,
        val message: String,
        val name: String
    )

    data class Result(
        val code: Code,
        val land: Land,
        val name: String,
        val region: Region
    ) {
        data class Code(
            val id: String,
            val mappingId: String,
            val type: String
        )

        data class Region(
            val area0: Area,
            val area1: Area,
            val area2: Area,
            val area3: Area,
            val area4: Area
        ) {
            data class Area(
                val coords: Coords,
                val name: String,
                val alias: String = ""
            )
        }

        data class Land(
            val addition0: Addition,
            val addition1: Addition,
            val addition2: Addition,
            val addition3: Addition,
            val addition4: Addition,
            val coords: Coords,
            val name: String="",
            val number1: String,
            val number2: String,
            val type: String
        ) {
            data class Addition(
                val type: String,
                val value: String
            )
        }
    }

    data class Coords(
        val center: Center
    ) {
        data class Center(
            val crs: String,
            val x: Double,
            val y: Double
        )
    }
}