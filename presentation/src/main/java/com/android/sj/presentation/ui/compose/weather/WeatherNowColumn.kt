package com.android.sj.presentation.ui.compose.weather

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.android.sj.presentation.R
import com.android.sj.presentation.models.state.WeatherViewState
import com.android.sj.presentation.ui.compose.common.ApiResultHandler
import com.android.sj.presentation.ui.previewParam.WeatherViewStatePreviewParamProvider
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.android.sj.presentation.utils.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun NowWeatherColumn(
    modifier: Modifier,
    weatherState: WeatherViewState,
    errorFunc: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.nowWeather),
            style = defaultTitleTextStyle()
        )
        ApiResultHandler(modifier, weatherState.weatherState, errorFunc = {errorFunc()}) { successState ->
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
                    successState.value.weatherImgDrawable
                )
                NowWeatherTextColumn(
                    Modifier
                        .weight(1f),
                    successState.value.nowTemp, successState.value.weatherText
                )
            }
            WeatherDetailsColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(2f),
                successState.value.nowRain, successState.value.nowWet, successState.value.nowWind
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun NowWeatherImageColumn(modifier: Modifier, weatherImg: Drawable?) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model = weatherImg,
            contentDescription = stringResource(R.string.loadingImage),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@Composable
fun NowWeatherTextColumn(modifier: Modifier, nowTemp: String, weatherText: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = nowTemp,
            fontSize = dimensionResource(R.dimen.NowTempText).sp(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = weatherText,
            fontSize = dimensionResource(R.dimen.NowWeatherText).sp(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun WeatherDetailsColumn(
    modifier: Modifier,
    rain: String,
    wet: String,
    wind: String
) {
    val nowWetRainWindSize = dimensionResource(R.dimen.NowWetRainWindColumn).sp()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = wet,
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
        )
        Text(
            text = rain,
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
        )
        Text(
            text = wind,
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun PreviewNowColumn(
    @PreviewParameter(WeatherViewStatePreviewParamProvider::class) weatherState: WeatherViewState
) {
    NowWeatherColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp), weatherState = weatherState, errorFunc = {}
    )
}