package com.project.newweatheropenapi.ui.compose.weather

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.enum.WeatherImgEnum
import com.project.newweatheropenapi.enum.imgConvert
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeekRainSkyResponse
import com.project.newweatheropenapi.ui.compose.TopLocationButton
import com.project.newweatheropenapi.ui.theme.Color_bbdefb
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.RAIN_MM
import com.project.newweatheropenapi.utils.RAIN_MM_NOW
import com.project.newweatheropenapi.utils.RAIN_TYPE
import com.project.newweatheropenapi.utils.SKY
import com.project.newweatheropenapi.utils.TMP
import com.project.newweatheropenapi.utils.WET
import com.project.newweatheropenapi.utils.WIND_DIR
import com.project.newweatheropenapi.utils.WIND_POWER
import com.project.newweatheropenapi.utils.dataPotalResultCode
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.managers.ComposeHelpManager
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import com.project.newweatheropenapi.utils.nowWetConvert
import com.project.newweatheropenapi.utils.rainConvert
import com.project.newweatheropenapi.utils.skyConvert
import com.project.newweatheropenapi.utils.skyImgEnum
import com.project.newweatheropenapi.utils.tempConvert
import com.project.newweatheropenapi.utils.weatherRainImgConvert
import com.project.newweatheropenapi.utils.windDir
import com.project.newweatheropenapi.utils.windPower
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

@Composable
fun WeatherScreenUI(onNavigate: () -> Unit, address: String, viewModel: WeatherViewModel) {
    val backgroundColor = Color_bbdefb
    val paddingStart = ComposeHelpManager.previewDimenResource(R.dimen.PaddingStart, 14.0f)
    val paddingEnd = ComposeHelpManager.previewDimenResource(R.dimen.PaddingEnd, 10.0f)

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
                .weight(11f)
                .background(backgroundColor)
                .padding(start = paddingStart.dp, end = paddingEnd.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TitleColumn()
            NowWeatherColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
                weatherState
            )
            TimeWeatherColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
                timeWeatherState
            )
            WeekForecastSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                weekRainSkyState
            )
        }
    }
}

@Preview
@Composable
fun TitleColumn() {
    val text = ComposeHelpManager.previewStringResource(R.string.nowWeather, "현재날씨")
    val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.WeatherTitle, 28.0f).sp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun NowWeatherColumn(modifier: Modifier, weatherState: ApiResult<WeatherResponse>) {
    val context = LocalContext.current
    when (weatherState) {
        is ApiResult.Success -> {
            if (weatherState.value.response.header.resultCode!= NO_ERROR){
                Box(modifier = modifier) {
                    Text("실패")
                }
                weatherState.value.response.header.resultCode.dataPotalResultCode(context)
            }else{
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
                        logMessage(item.category)
                        when (item.category) {
                            TMP -> nowTemp = item.fcstValue.tempConvert(context)
                            RAIN_MM -> nowRain = item.fcstValue.rainConvert(context)
                            WET -> nowWet = item.fcstValue.nowWetConvert(context)
                            WIND_DIR -> windDir = item.fcstValue.windDir(context)
                            WIND_POWER -> nowWind = item.fcstValue.windPower(context, windDir)
                            RAIN_TYPE -> weatherImg = item.fcstValue.weatherRainImgConvert()
                            SKY -> { weatherImgDrawable = item.fcstValue.skyImgEnum(weatherImg).imgConvert(context)
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
                        nowRain, nowWet, nowWind)
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

        is ApiResult.Loading -> {}
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TimeWeatherColumn(modifier: Modifier, timeWeatherState: ApiResult<WeatherResponse>) {
    val context = LocalContext.current
    val timeWeatherText = ComposeHelpManager.previewStringResource(R.string.timeWeather, "시간대별 예보")
    val loadingImageText =
        ComposeHelpManager.previewStringResource(R.string.loadingImage, "이미지 로딩중")
    val timeWeatherTextSize =
        ComposeHelpManager.previewDimenResource(R.dimen.WeatherRvTitle, 24.0f).sp

    when (timeWeatherState) {
        is ApiResult.Success -> {
            if (timeWeatherState.value.response.header.resultCode!= NO_ERROR){
                Box(modifier = modifier) {
                    Text("실패")
                }
                timeWeatherState.value.response.header.resultCode.dataPotalResultCode(context)
            }else{

//                var timeTemp by remember { mutableStateOf("") }
//                var timeRain by remember { mutableStateOf("") }
//                var timeWet by remember { mutableStateOf("") }
//                var timeWind by remember { mutableStateOf("") }
//                var weatherImg by remember { mutableStateOf(WeatherImgEnum.None) }
//                var weatherImgDrawable by remember { mutableStateOf<Drawable?>(null) }
//                var weatherText by remember { mutableStateOf("") }
//                var windDir by remember { mutableStateOf("") }
//
//                val weatherItem = timeWeatherState.value.response.body.items.item
//
//                weatherItem.forEach { item ->
//                    if (item.fcstTime.toInt() - item.baseTime.toInt() < 100) {
//                        logMessage(item.category)
//                        when (item.category) {
//                            TMP -> timeTemp = item.fcstValue.tempConvert(context)
//                            RAIN_MM_NOW -> timeRain = item.fcstValue.rainConvert(context)
//                            WET -> timeWet = item.fcstValue.nowWetConvert(context)
//                            WIND_DIR -> windDir = item.fcstValue.windDir(context)
//                            WIND_POWER -> timeWind = item.fcstValue.windPower(context, windDir)
//                            RAIN_TYPE -> weatherImg = item.fcstValue.weatherRainImgConvert()
//                            SKY -> { weatherImgDrawable = item.fcstValue.skyImgEnum(weatherImg).imgConvert(context)
//                                weatherText = item.fcstValue.skyConvert(context)
//                            }
//                        }
//                    }
//                }

                Column(
                    modifier = modifier
                ) {
                    GlideImage(
                        model = R.drawable.dotted_line_long,
                        contentDescription = loadingImageText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Text(
                        text = timeWeatherText,
                        fontSize = timeWeatherTextSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Box(Modifier.fillMaxSize())
//                    HorizontalPager(modifier = Modifier.fillMaxSize(),
//                        contentPadding = PaddingValues(horizontal = 16.dp)
//                    ) { }
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

        is ApiResult.Loading -> {}
    }
}

@Composable
fun WeekForecastSection(modifier: Modifier, weekRainSkyState: ApiResult<WeekRainSkyResponse>) {
    when (weekRainSkyState) {
        is ApiResult.Success -> {
            Column(
                modifier = modifier
            ) {
//        Image(
//            painter = painterResource(id = R.drawable.dotted_line_long),
//            contentDescription = stringResource(id = R.string.imageLoadFail),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )

//        Text(
//            text = stringResource(id = R.string.weekFcst),
//            fontSize = dimensionResource(id = R.dimen.WeatherRvTitle).toSp(),
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .align(Alignment.CenterHorizontally)
//        )

//        RecyclerView(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp) // Adjust height as needed
//        )
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

        is ApiResult.Loading -> {}
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun NowWeatherImageColumn(modifier: Modifier, weatherImg: Drawable?) {
    val loadingImageText = ComposeHelpManager.previewStringResource(R.string.loadingImage, "이미지 로딩중")
    val imageSize = ComposeHelpManager.previewDimenResource(R.dimen.NowFcstIV, 110.0f).dp

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = weatherImg,
            contentDescription = loadingImageText,
            modifier = Modifier
                .size(imageSize)
        )
    }
}

@Composable
fun NowWeatherTextColumn(modifier: Modifier, nowTemp: String, weatherText: String) {
    val nowTempSize = ComposeHelpManager.previewDimenResource(R.dimen.NowTempText, 64.0f).sp
    val nowWeatherSize = ComposeHelpManager.previewDimenResource(R.dimen.NowWeatherText, 30.0f).sp

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = nowTemp,
            fontSize = nowTempSize,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = weatherText,
            fontSize = nowWeatherSize,
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
    val nowWetRainWindSize =
        ComposeHelpManager.previewDimenResource(R.dimen.NowWetRainWindColumn, 18.0f).sp

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
        )
        Text(
            text = rain,
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            text = wind,
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}