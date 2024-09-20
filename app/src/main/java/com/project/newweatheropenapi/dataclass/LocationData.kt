package com.project.newweatheropenapi.dataclass

import com.naver.maps.geometry.LatLng

data class LocationData(
    var latLng: LatLng,
    var address : String="",
    var x : String="",
    var y : String=""
)
