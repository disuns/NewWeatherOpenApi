package com.android.sj.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.ui.compose.InitScreen
import com.android.sj.presentation.ui.theme.NewWeatherOpenApiTheme
import com.android.sj.presentation.utils.LocalAirQualityVM
import com.android.sj.presentation.utils.LocalLocationDataManager
import com.android.sj.presentation.utils.LocalNavController
import com.android.sj.presentation.utils.LocalNaverMapVM
import com.android.sj.presentation.utils.LocalWeatherVM
import com.android.sj.presentation.viewmodels.AirQualityViewModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import com.android.sj.presentation.viewmodels.WeatherViewModel
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
                val navController = rememberNavController()

                val naverMapVM = hiltViewModel<NaverMapViewModel>()
                val weatherVM = hiltViewModel<WeatherViewModel>()
                val airQualityVM = hiltViewModel<AirQualityViewModel>()

                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalLocationDataManager provides locationDataManager,
                    LocalWeatherVM provides weatherVM,
                    LocalAirQualityVM provides airQualityVM,
                    LocalNaverMapVM provides naverMapVM
                ) {
                    Box(Modifier.safeDrawingPadding().semantics {
                        testTagsAsResourceId = true
                    }){
                        InitScreen()

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
}