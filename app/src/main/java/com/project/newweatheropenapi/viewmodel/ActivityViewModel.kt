package com.project.newweatheropenapi.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.common.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor() :BaseViewModel(){
    private val _locationData = mutableStateOf<LocationData?>(null)
    val locationData = _locationData

    fun updateLocationData(latLng: LatLng, address:String){
        _locationData.value = LocationData(latLng, address)
    }
}