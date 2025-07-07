package com.android.sj.presentation.ui.compose.airquality

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.sj.presentation.ui.compose.airQuality.predictionmodel.PredictionModelColumnCommon
import com.android.sj.presentation.utils.LocalAirQualityVM

@Composable
fun PredictionModelColumn(modifier: Modifier) {
    val viewModel = LocalAirQualityVM.current
    PredictionModelColumnCommon(
        modifier = modifier,
        errorFunc = {
            viewModel.fetchAirQuality()
        }
    )
}