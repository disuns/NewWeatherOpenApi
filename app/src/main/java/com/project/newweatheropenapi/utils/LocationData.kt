package com.project.newweatheropenapi.utils

import com.naver.maps.geometry.LatLng

data class LocationData(
    var latLng: LatLng,
    var address : String
)
