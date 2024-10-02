package com.project.newweatheropenapi.ui.compose.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.compose.common.DotLineColumn
import com.project.newweatheropenapi.ui.theme.Default_BackGround
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel,
    nowErrorFunc: () -> Unit,
    timeErrorFunc: () -> Unit,
    weekErrorFunc: () -> Unit
) {
    val weatherState by viewModel.state.collectAsState()

    weatherState.isAllLoading()

    Column(
        modifier = modifier
            .background(Default_BackGround)
            .padding(
                start = dimensionResource(R.dimen.PaddingStart),
                end = dimensionResource(R.dimen.PaddingEnd)
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NowWeatherColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f),
            weatherState
        ) { nowErrorFunc() }
        DotLineColumn()
        TimeWeatherColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f),
            weatherState
        ) { timeErrorFunc() }
        DotLineColumn()
        WeekWeatherColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            weatherState
        ) { weekErrorFunc() }
    }
}