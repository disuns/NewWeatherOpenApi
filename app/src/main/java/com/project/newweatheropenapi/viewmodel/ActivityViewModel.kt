package com.project.newweatheropenapi.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.app.WeatherApplication
import com.project.newweatheropenapi.common.LocationData
import com.project.newweatheropenapi.common.logMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor() : BaseViewModel() {

    private val _locationData = mutableStateOf<LocationData?>(null)
    val locationData = _locationData

    private fun isEmulator() = Build.FINGERPRINT.contains("emulator")

    fun getLocation(fusedLocationClient : FusedLocationProviderClient,onLocationFetched: (LatLng) -> Unit) {
        logMessage(Build.FINGERPRINT)
        if (isEmulator()) {
            getGps(fusedLocationClient, onLocationFetched)
//            _locationData.value = LocationData(LatLng(lat=37.51982548626224, lon = 126.88237267230349))
        } else {
            getGps(fusedLocationClient, onLocationFetched)
        }
    }

    @SuppressLint("MissingPermission")
    fun getGps(fusedLocationClient : FusedLocationProviderClient,onLocationFetched: (LatLng) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val latLng = LatLng(it.latitude, it.longitude)
                onLocationFetched(latLng)
            }
        }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    fun updateLocationData(latLng: LatLng, address: String) {
        logMessage( "$latLng + $address")
        _locationData.value = LocationData(latLng, address)
    }
}