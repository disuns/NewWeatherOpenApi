package com.android.sj.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.android.sj.common.utils.managers.LocationDataManager
import com.android.sj.presentation.ui.compose.InitScreen
import com.android.sj.presentation.ui.compose.loading.SplashDialogScreen
import com.android.sj.presentation.ui.theme.NewWeatherOpenApiTheme
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