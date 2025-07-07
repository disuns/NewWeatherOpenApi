package com.android.sj.presentation.ui.compose.airquality

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.sj.presentation.ui.compose.airQuality.measuringstation.MeasuringStationColumnCommon
import com.android.sj.presentation.utils.LocalAirQualityVM

@Composable
fun MeasuringStationColumn(
    modifier: Modifier
) {
    val viewModel = LocalAirQualityVM.current
    
    MeasuringStationColumnCommon(
        modifier = modifier,
        onLoadStation = { stationName->
            viewModel.fetchRltmStation(stationName)
        },
        errorFunc = { x, y->
            viewModel.fetchStationFindAndThenRltmStation(x, y)
        }
    )
}