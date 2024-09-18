package com.project.newweatheropenapi.ui.compose.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.ui.compose.TopLocationButton
import com.project.newweatheropenapi.ui.theme.Color_bbdefb
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import com.project.newweatheropenapi.utils.sp
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

@Composable
fun WeatherScreenUI(onNavigate: () -> Unit, address: String, viewModel: WeatherViewModel) {
    val weatherState by viewModel.weatherStateFlow.collectAsState()
    val timeWeatherState by viewModel.timeWeatherState.collectAsState()
    val weekRainSkyState by viewModel.weekRainSkyState.collectAsState()

    val isLoading = weatherState is ApiResult.Loading ||
            timeWeatherState is ApiResult.Loading ||
            weekRainSkyState is ApiResult.Loading

    LoadingStateManager.isShow(isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopLocationButton(
            onClick = onNavigate,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            query = address
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(14f)
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
            TimeWeatherColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f),
                timeWeatherState
            )
            WeekWeatherColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
                weekRainSkyState
            )
        }
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
            style = TextStyle(
                fontSize = dimensionResource(R.dimen.WeatherTitle).sp(),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PreviewTitleColumn(){
    TitleColumn()
}

