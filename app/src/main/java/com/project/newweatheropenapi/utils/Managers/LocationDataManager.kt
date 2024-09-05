package com.project.newweatheropenapi.utils.Managers

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.utils.LocationData
import com.project.newweatheropenapi.utils.logMessage
import javax.inject.Inject

class LocationDataManager @Inject constructor(
    private val fusedLocationClient : FusedLocationProviderClient
) {
    private val _locationData = mutableStateOf(LocationData(LatLng(0.0,0.0),""))
    val locationData : State<LocationData> = _locationData

    @SuppressLint("MissingPermission")
    fun getGps(onLocationFetched: (LatLng) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    onLocationFetched(latLng)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun updateLocationData(latLng: LatLng, address: String) {
        _locationData.value = LocationData(latLng, address)
    }
}