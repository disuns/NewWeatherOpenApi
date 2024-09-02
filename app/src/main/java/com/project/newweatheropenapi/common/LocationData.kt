package com.project.newweatheropenapi.common

import com.naver.maps.geometry.LatLng

data class LocationData(
    val latLng: LatLng,
    val address : String
)
