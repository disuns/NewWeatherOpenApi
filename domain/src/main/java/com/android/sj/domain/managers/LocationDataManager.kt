package com.android.sj.domain.managers

import com.android.sj.domain.models.info.LocationInfo
import kotlinx.coroutines.flow.StateFlow

interface LocationDataManager {
    fun getGps(onStopGps : ()->Unit, onLocationFetched: (Double, Double) -> Unit)
    fun updateLocationData(lat : Double = 0.0, lon : Double = 0.0, address: String = "", x : String = "", y : String = "")
    val locationData: StateFlow<LocationInfo>
}