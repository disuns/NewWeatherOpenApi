package com.android.sj.presentation.ui.compose.airQuality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.android.sj.presentation.R
import com.android.sj.presentation.ui.compose.airquality.AirQualityColumn
import com.android.sj.presentation.ui.compose.airquality.MeasuringStationColumn
import com.android.sj.presentation.ui.compose.airquality.PredictionModelColumn
import com.android.sj.presentation.ui.compose.common.DotLineColumn
import com.android.sj.presentation.ui.theme.Default_BackGround

@Composable
fun AirQualityScreen(modifier: Modifier) {
    LazyColumn (
        modifier = modifier
            .background(Default_BackGround)
            .padding(
                start = dimensionResource(R.dimen.PaddingStart),
                end = dimensionResource(R.dimen.PaddingEnd)
            ),
    ) {
        item {
            MeasuringStationColumn(modifier = Modifier.fillMaxWidth())
        }
        item { DotLineColumn() }
        item {
            AirQualityColumn(modifier = Modifier.fillMaxWidth())
        }
        item { DotLineColumn() }
        item {
            PredictionModelColumn(modifier = Modifier.fillMaxWidth())
        }
    }
}