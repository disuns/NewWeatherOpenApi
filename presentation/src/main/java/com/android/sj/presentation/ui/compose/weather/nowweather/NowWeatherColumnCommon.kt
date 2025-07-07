package com.android.sj.presentation.ui.compose.weather.nowweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.sj.presentation.R
import com.android.sj.presentation.models.uimodels.weather.WeatherUIModel
import com.android.sj.presentation.state.viewstate.WeatherViewState
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.ui.previewParam.WeatherViewStatePreviewParamProvider
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.android.sj.presentation.utils.LocalLocationDataManager
import com.android.sj.presentation.utils.LocalWeatherVM

@Composable
fun NowWeatherColumnCommon(
    modifier: Modifier,
    errorFunc: (String, String) -> Unit
) {
    val weatherState by LocalWeatherVM.current.viewState.collectAsStateWithLifecycle()

    val locationDataManager = LocalLocationDataManager.current
    val locationValue by locationDataManager.locationData.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.nowWeather),
            style = defaultTitleTextStyle()
        )
        UiStateHandler(modifier, weatherState.weatherUiState, errorFunc = {errorFunc(locationValue.lat.toString(), locationValue.lng.toString())}) { successState ->
            NowWeatherColumnContent(successState)
        }
    }
}

@Composable
fun ColumnScope.NowWeatherColumnContent(data: WeatherUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .weight(3.5f)
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NowWeatherImageColumn(
            Modifier
                .weight(1.5f),
            data.weatherImgDrawable
        )
        NowWeatherTextColumn(
            Modifier
                .weight(1f),
            data.nowTemp, data.weatherText
        )
    }
    WeatherDetailsColumn(
        Modifier
            .fillMaxWidth()
            .weight(2f),
        data.nowRain, data.nowWet, data.nowWind
    )
}

@Preview
@Composable
fun PreviewNowColumn(
    @PreviewParameter(WeatherViewStatePreviewParamProvider::class) weatherState: WeatherViewState
) {
    val data = weatherState.weatherUiState.model!!
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .height(400.dp)) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.nowWeather),
            style = defaultTitleTextStyle()
        )
        NowWeatherColumnContent(data)
    }
}