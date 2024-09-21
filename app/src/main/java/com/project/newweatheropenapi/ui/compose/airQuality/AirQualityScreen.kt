package com.project.newweatheropenapi.ui.compose.airQuality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.compose.common.DotLineColumn
import com.project.newweatheropenapi.ui.theme.Color_bbdefb
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import com.project.newweatheropenapi.viewmodel.AirQualityViewModel

@Composable
fun AirQualityScreen(
    modifier: Modifier,
    viewModel: AirQualityViewModel,
) {
    val rltmStationState by viewModel.rltmStationStateFlow.collectAsState()
    val stationFindState by viewModel.stationFindStateFlow.collectAsState()
    val airQualityState by viewModel.airQualityStateFlow.collectAsState()

    LoadingStateManager.isAnyLoadingCheck(rltmStationState, airQualityState, stationFindState)

    LazyColumn (
        modifier = modifier
            .background(Color_bbdefb)
            .padding(
                start = dimensionResource(R.dimen.PaddingStart),
                end = dimensionResource(R.dimen.PaddingEnd)
            ),
    ) {
        item {
            MeasuringStationColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                stationFindState,
                rltmStationState)
        }
        item { DotLineColumn() }
        item {
            AirQualityColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                airQualityState)
        }
        item { DotLineColumn() }
        item {
            PredictionModelColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                airQualityState)
        }
    }
}