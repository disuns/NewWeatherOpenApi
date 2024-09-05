package com.project.newweatheropenapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.project.newweatheropenapi.ui.compose.InitScreen
import com.project.newweatheropenapi.ui.theme.NewWeatherOpenApiTheme
import com.project.newweatheropenapi.viewmodel.ActivityViewModel
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val activityViewModel: ActivityViewModel = hiltViewModel()
            val naverMapViewModel: NaverMapViewModel = hiltViewModel()

            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
            NewWeatherOpenApiTheme{
                activityViewModel.getLocation(fusedLocationClient) { latLng ->
                    val coords = "${latLng.latitude},${latLng.longitude}"
                    naverMapViewModel.fetchNaverMap(coords)
                }
                InitScreen(activityViewModel)
            }
        }
    }
}