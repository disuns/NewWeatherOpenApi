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
import com.project.newweatheropenapi.utils.Managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.ActivityViewModel
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var locationDataManager: LocationDataManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewWeatherOpenApiTheme{
                InitScreen(locationDataManager)
            }
        }
    }
}