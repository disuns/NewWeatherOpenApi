package com.project.newweatheropenapi.ui.compose.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.compose.common.DotLineColumn
import com.project.newweatheropenapi.ui.theme.Color_bbdefb
import com.project.newweatheropenapi.ui.theme.defaultTitleTextStyle
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

@Composable
fun WeatherScreenUI(
    modifier: Modifier,
    viewModel: WeatherViewModel
) {
    val weatherState by viewModel.weatherStateFlow.collectAsState()
    val timeWeatherState by viewModel.timeWeatherState.collectAsState()
    val weekRainSkyState by viewModel.weekRainSkyState.collectAsState()

    LoadingStateManager.isAnyLoadingCheck(weatherState, timeWeatherState, weekRainSkyState)

    Column(
        modifier = modifier
            .background(Color_bbdefb)
            .padding(
                start = dimensionResource(R.dimen.PaddingStart),
                end = dimensionResource(R.dimen.PaddingEnd)
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitleColumn()
        NowWeatherColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f),
            weatherState
        )
        DotLineColumn()
        TimeWeatherColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f),
            timeWeatherState
        )
        DotLineColumn()
        WeekWeatherColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            weekRainSkyState
        )
    }
}

@Composable
fun TitleColumn() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.nowWeather),
            style = defaultTitleTextStyle(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PreviewTitleColumn(){
    TitleColumn()
}

