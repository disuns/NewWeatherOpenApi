package com.project.newweatheropenapi.utils.managers

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.dataclass.LocationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LocationDataManager @Inject constructor(
    private val fusedLocationClient : FusedLocationProviderClient
) {
    private val _locationData = MutableStateFlow(LocationData(LatLng(0.0,0.0)))
    val locationData : StateFlow<LocationData> = _locationData.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getGps(onLocationFetched: (Double, Double) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                location?.let {
                    onLocationFetched(it.latitude, it.longitude)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun updateLocationData(latLng: LatLng, address: String = "", x : String = "", y : String = "") {
        _locationData.value = LocationData(latLng, address, x, y)
    }
}