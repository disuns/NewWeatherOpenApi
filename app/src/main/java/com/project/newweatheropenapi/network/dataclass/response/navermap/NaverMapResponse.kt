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

        data class Land(
            val additions: List<Addition>,
            val coords: Coords,
            val name: String,
            val number1: String,
            val number2: String,
            val type: String
        ) {
            data class Addition(
                val type: String,
                val value: String
            )
        }

        data class Region(
            val areas: List<Area> // Assume all areas are similar and use a list
        ) {
            data class Area(
                val coords: Coords,
                val name: String,
                val alias: String? = null
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