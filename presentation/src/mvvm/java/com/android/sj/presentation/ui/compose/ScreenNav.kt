package com.android.sj.presentation.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.android.sj.presentation.ui.compose.nav.ScreenNavCommon
import com.android.sj.presentation.utils.LocalAirQualityVM
import com.android.sj.presentation.utils.LocalNaverMapVM
import com.android.sj.presentation.utils.LocalWeatherVM
import kotlinx.coroutines.flow.merge

@Composable
fun ScreenNav(paddingValues: PaddingValues) {
    val naverMapVM = LocalNaverMapVM.current
    val weatherVM = LocalWeatherVM.current
    val airQualityVM = LocalAirQualityVM.current

    ScreenNavCommon(
        paddingValues = paddingValues,
        mergedEventEffect = remember(naverMapVM, weatherVM, airQualityVM) {
            merge(naverMapVM.events, weatherVM.events, airQualityVM.events)
        },
        getLocation = {
            naverMapVM.getLocation()
        },
        weatherFetchAll = { nx, ny, address ->
            weatherVM.fetchAllWeatherData(nx = nx, ny = ny, address = address)
        },
        airQualityFetchAll = { x, y ->
            airQualityVM.fetchAllAirQualityData(regionX = x, regionY = y)
        }
    )
}