package com.android.sj.presentation.ui.compose.airquality

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.sj.presentation.ui.compose.airQuality.airquality.AirQualityColumnCommon
import com.android.sj.presentation.utils.LocalAirQualityVM

@Composable
fun AirQualityColumn(
    modifier: Modifier
) {
    val viewModel = LocalAirQualityVM.current
    AirQualityColumnCommon(modifier) {
        viewModel.fetchAirQuality()
    }
}
