package com.project.newweatheropenapi

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.naver.maps.geometry.LatLng
import com.project.newweatheropenapi.common.DataConverter
import com.project.newweatheropenapi.common.LocationData
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.request.navermap.NaverMapRequest
import com.project.newweatheropenapi.network.dataclass.request.navermap.toMap
import com.project.newweatheropenapi.network.repository.NaverMapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val naverMapRepository: NaverMapRepository,
    private val dataConverter: DataConverter
) {
//    private val _locationData = MutableStateFlow<LocationData?>(null)
//    val locationData: StateFlow<LocationData?> = _locationData
//
//    @SuppressLint("MissingPermission")
//    fun fetchLocation(onLocationFetched: (LatLng) -> Unit) {
//        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//            location?.let {
//                val latLng = LatLng(it.latitude, it.longitude)
//                onLocationFetched(latLng)
//            }
//        }.addOnFailureListener { e ->
//            e.printStackTrace()
//        }
//    }
//
//    suspend fun fetchAddressForCoordinates(coords: String) {
//        val result = naverMapRepository.getReverseGeoCo(NaverMapRequest(coords).toMap())
//        if (result is ApiResult.Success) {
//            val address = dataConverter.mapAddressConvert(result.value)
//            _locationData.value = LocationData(
//                LatLng(result.value.results[0].region.area0.coords.center.x,
//                    result.value.results[0].region.area0.coords.center.y),
//                address
//            )
//        }
//    }
}