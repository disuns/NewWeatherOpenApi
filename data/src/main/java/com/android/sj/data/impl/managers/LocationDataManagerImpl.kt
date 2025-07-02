package com.android.sj.data.impl.managers

import android.Manifest
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.models.info.LocationInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LocationDataManagerImpl @Inject constructor(
    private val fusedLocationClient : FusedLocationProviderClient
) : LocationDataManager {
    private val _locationData = MutableStateFlow(LocationInfo())
    override val locationData : StateFlow<LocationInfo> = _locationData.asStateFlow()

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION]
    )
    override fun getGps(onStopGps : ()->Unit,onLocationFetched: (Double, Double) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                location?.let {
                    onLocationFetched(it.latitude, it.longitude)
                    fusedLocationClient.removeLocationUpdates(this)
                    onStopGps()
                }
            }
            override fun onLocationAvailability(locationAvailability: com.google.android.gms.location.LocationAvailability) {
                if (!locationAvailability.isLocationAvailable) {
                    onStopGps()
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    override fun updateLocationData(lat : Double, lon : Double, address: String, x : String, y : String) {
        _locationData.value = LocationInfo(lat, lon, address, x, y)
    }
}