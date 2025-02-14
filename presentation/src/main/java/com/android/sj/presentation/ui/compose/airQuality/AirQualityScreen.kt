package com.android.sj.presentation.ui.compose.airQuality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.android.sj.presentation.R
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.ui.compose.common.DotLineColumn
import com.android.sj.presentation.ui.theme.Default_BackGround
import com.android.sj.presentation.viewmodels.AirQualityViewModel

@Composable
fun AirQualityScreen(
    modifier: Modifier,
    viewModel: AirQualityViewModel,
    stationFindErrorFunc : () -> Unit,
    airQualityErrorFunc : () -> Unit
) {
    val airQualityViewState by viewModel.state.collectAsState(initial = viewModel.initialState)

    airQualityViewState.isAllLoading()

    LazyColumn (
        modifier = modifier
            .background(Default_BackGround)
            .padding(
                start = dimensionResource(R.dimen.PaddingStart),
                end = dimensionResource(R.dimen.PaddingEnd)
            ),
    ) {
        item {
            MeasuringStationColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                airQualityState = airQualityViewState,
                onLoadStation = { stationName ->
                    viewModel.handleIntent(AirQualityIntent.LoadRltmStation(stationName))
                }
            ){stationFindErrorFunc()}
        }
        item { DotLineColumn() }
        item {
            AirQualityColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                airQualityViewState){airQualityErrorFunc()}
        }
        item { DotLineColumn() }
        item {
            PredictionModelColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                airQualityViewState){airQualityErrorFunc()}
        }
    }
}