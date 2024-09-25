package com.project.newweatheropenapi.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import com.project.newweatheropenapi.ui.compose.InitScreen
import com.project.newweatheropenapi.ui.compose.loading.SplashDialogScreen
import com.project.newweatheropenapi.ui.theme.NewWeatherOpenApiTheme
import com.project.newweatheropenapi.utils.managers.LocationDataManager
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
                Box(Modifier.safeDrawingPadding()){
                    InitScreen(locationDataManager)
                    SplashDialogScreen()

//                    Button(
//                        onClick = {
//                            throw RuntimeException("Test Crash") // Force a crash
//                        }
//                    ) {
//                        Text("Test Crash")
//                    }
                }

            }
        }
    }
}