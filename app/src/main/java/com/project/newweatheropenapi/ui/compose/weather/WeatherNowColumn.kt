package com.project.newweatheropenapi.ui.compose.weather

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.enum.WeatherImgEnum
import com.project.newweatheropenapi.enum.imgConvert
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
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
fun NowWeatherColumn(modifier: Modifier, weatherState: ApiResult<WeatherResponse>) {
    val context = LocalContext.current

    when (weatherState) {
        is ApiResult.Success -> {
            if (weatherState.value.response.header.resultCode != NO_ERROR) {
                Box(modifier = modifier) {
                    Text("실패")
                }
                weatherState.value.response.header.resultCode.dataPotalResultCode(context)
            } else {
                var nowTemp by remember { mutableStateOf("") }
                var nowRain by remember { mutableStateOf("") }
                var nowWet by remember { mutableStateOf("") }
                var nowWind by remember { mutableStateOf("") }
                var weatherImg by remember { mutableStateOf(WeatherImgEnum.None) }
                var weatherImgDrawable by remember { mutableStateOf<Drawable?>(null) }
                var weatherText by remember { mutableStateOf("") }
                var windDir by remember { mutableStateOf("") }

                val weatherItem = weatherState.value.response.body.items.item

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

                Column(modifier = modifier) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f)
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NowWeatherImageColumn(
                            Modifier
                                .fillMaxHeight()
                                .weight(2f),
                            weatherImgDrawable
                        )
                        NowWeatherTextColumn(
                            Modifier
                                .fillMaxHeight()
                                .weight(3f),
                            nowTemp, weatherText
                        )
                    }
                    WeatherDetailsColumn(
                        Modifier
                            .fillMaxWidth()
                            .weight(3f),
                        nowRain, nowWet, nowWind
                    )
                }
            }
        }

        is ApiResult.Empty -> {
            Box(modifier = modifier) {
                Text("비었다")
            }
        }

        is ApiResult.Error -> {
            Box(modifier = modifier) {
                Text("실패")
            }
        }

        is ApiResult.Loading -> {
            Box(modifier = modifier) {
                Text("로딩중")
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
                .size(dimensionResource(R.dimen.NowFcstIV))
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
    @PreviewParameter(WeatherResponsePreviewParamProvider::class) weatherState: ApiResult<WeatherResponse>
) {
    NowWeatherColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp), weatherState = weatherState
    )
}

class WeatherResponsePreviewParamProvider : PreviewParameterProvider<ApiResult<WeatherResponse>> {
    override val values = sequenceOf(
        ApiResult.Success(
            WeatherResponse(
                response = WeatherResponse.Response(
                    header = WeatherResponse.Response.Header(
                        resultCode = "00",
                        resultMsg = "NORMAL_SERVICE"
                    ),
                    body = WeatherResponse.Response.Body(
                        dataType = "JSON",
                        items = WeatherResponse.Response.Body.Items(
                            item = mutableListOf(
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "LGT",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "LGT",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1300",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "LGT",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1400",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "PTY",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "RN1",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "강수없음"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "SKY",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "1"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "T1H",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "28"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "REH",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "60"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "UUU",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "-2"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "VVV",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "0.1"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "VEC",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "94"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "WSD",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "2"
                                )
                            )
                        ),
                        numOfRows = 1000,
                        pageNo = 1,
                        totalCount = 60
                    )
                )
            )
        )
    )
}