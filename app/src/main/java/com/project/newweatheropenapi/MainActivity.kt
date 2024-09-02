package com.project.newweatheropenapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newweatheropenapi.ui.compose.InitScreen
import com.project.newweatheropenapi.ui.theme.NewWeatherOpenApiTheme
import com.project.newweatheropenapi.viewmodel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val activityViewModel: ActivityViewModel = hiltViewModel()
            NewWeatherOpenApiTheme{
                InitScreen(activityViewModel)
            }
        }
    }
}