package com.project.newweatheropenapi.ui.compose.weather

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.dataclass.state.WeatherViewState
import com.project.newweatheropenapi.enum.WeatherImgEnum
import com.project.newweatheropenapi.enum.imgConvert
import com.project.newweatheropenapi.ui.compose.common.ApiResultHandler
import com.project.newweatheropenapi.ui.compose.common.DataPotalSuccesError
import com.project.newweatheropenapi.ui.previewParamAndService.WeatherViewStatePreviewParamProvider
import com.project.newweatheropenapi.ui.theme.defaultTitleTextStyle
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.RAIN_MM_NOW
import com.project.newweatheropenapi.utils.RAIN_TYPE
import com.project.newweatheropenapi.utils.SKY
import com.project.newweatheropenapi.utils.TMP_NOW
import com.project.newweatheropenapi.utils.WET
import com.project.newweatheropenapi.utils.WIND_DIR
import com.project.newweatheropenapi.utils.WIND_POWER
import com.project.newweatheropenapi.utils.dataPotalResultCode
import com.project.newweatheropenapi.utils.nowRainConvert
import com.project.newweatheropenapi.utils.nowWetConvert
import com.project.newweatheropenapi.utils.skyConvert
import com.project.newweatheropenapi.utils.skyImgEnum
import com.project.newweatheropenapi.utils.sp
import com.project.newweatheropenapi.utils.tempConvert
import com.project.newweatheropenapi.utils.weatherRainImgConvert
import com.project.newweatheropenapi.utils.windDir
import com.project.newweatheropenapi.utils.windPower

@Composable
fun NowWeatherColumn(
    modifier: Modifier,
    weatherState: WeatherViewState,
    errorFunc: () -> Unit
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.nowWeather),
            style = defaultTitleTextStyle()
        )
        ApiResultHandler(modifier, weatherState.weatherState, errorFunc = {errorFunc()}) { successState ->
            if (successState.value.response.header.resultCode != NO_ERROR) {
                DataPotalSuccesError(modifier, successState.value.response.header.resultCode.dataPotalResultCode(context))
            } else {
                var nowTemp by remember { mutableStateOf("") }
                var nowRain by remember { mutableStateOf("") }
                var nowWet by remember { mutableStateOf("") }
                var nowWind by remember { mutableStateOf("") }
                var weatherImg by remember { mutableStateOf(WeatherImgEnum.None) }
                var weatherImgDrawable by remember { mutableStateOf<Drawable?>(null) }
                var weatherText by remember { mutableStateOf("") }
                var windDir by remember { mutableStateOf("") }

                val weatherItem = successState.value.response.body.items.item

                weatherItem.forEach { item ->
                    if (item.fcstTime.toInt() - item.baseTime.toInt() < 100) {
                        when (item.category) {
                            TMP_NOW -> nowTemp = item.fcstValue.tempConvert(context)
                            RAIN_MM_NOW -> nowRain = item.fcstValue.nowRainConvert(context)
                            WET -> nowWet = item.fcstValue.nowWetConvert(context)
                            WIND_DIR -> windDir = item.fcstValue.windDir(context)
                            WIND_POWER -> nowWind = item.fcstValue.windPower(context, windDir)
                            RAIN_TYPE -> weatherImg = item.fcstValue.weatherRainImgConvert()
                            SKY -> {
                                weatherImgDrawable =
                                    item.fcstValue.skyImgEnum(weatherImg).imgConvert(context)
                                weatherText = item.fcstValue.skyConvert(context)
                            }
                        }
                    }
                }

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
                        weatherImgDrawable
                    )
                    NowWeatherTextColumn(
                        Modifier
                            .weight(1f),
                        nowTemp, weatherText
                    )
                }
                WeatherDetailsColumn(
                    Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    nowRain, nowWet, nowWind
                )
            }
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