package com.project.newweatheropenapi.ui.compose.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.dataclass.TimeWeatherData
import com.project.newweatheropenapi.enum.imgConvert
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
import com.project.newweatheropenapi.ui.theme.Color_7192ad
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.RAIN_MM
import com.project.newweatheropenapi.utils.RAIN_PER
import com.project.newweatheropenapi.utils.RAIN_TYPE
import com.project.newweatheropenapi.utils.SKY
import com.project.newweatheropenapi.utils.TMP_TIME
import com.project.newweatheropenapi.utils.WET
import com.project.newweatheropenapi.utils.WIND_DIR
import com.project.newweatheropenapi.utils.WIND_POWER
import com.project.newweatheropenapi.utils.dataPotalResultCode
import com.project.newweatheropenapi.utils.dateConvert
import com.project.newweatheropenapi.utils.rainConvert
import com.project.newweatheropenapi.utils.rainPerConvert
import com.project.newweatheropenapi.utils.skyImgEnum
import com.project.newweatheropenapi.utils.sp
import com.project.newweatheropenapi.utils.tempConvert
import com.project.newweatheropenapi.utils.timeDataConvert
import com.project.newweatheropenapi.utils.weatherRainImgConvert
import com.project.newweatheropenapi.utils.wetConvert
import com.project.newweatheropenapi.utils.windDir
import com.project.newweatheropenapi.utils.windPower
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TimeWeatherColumn(modifier: Modifier, timeWeatherState: ApiResult<WeatherResponse>) {
    val context = LocalContext.current

    when (timeWeatherState) {
        is ApiResult.Success -> {
            if (timeWeatherState.value.response.header.resultCode != NO_ERROR) {
                Box(modifier = modifier) {
                    Text("실패")
                }
                timeWeatherState.value.response.header.resultCode.dataPotalResultCode(context)
            } else {
                val list = timeDataList(timeWeatherState.value)
                val pagerState = rememberPagerState(
                    pageCount = { list.size }
                )
                Column(
                    modifier = modifier
                ) {
                    GlideImage(
                        model = R.drawable.dotted_line_long,
                        contentDescription = stringResource(R.string.loadingImage),
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = stringResource(R.string.timeWeather),
                        fontSize = dimensionResource(R.dimen.WeatherViewTitle).sp(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp)
                    )

                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp),
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.CardViewPadding)),
                        pageSpacing = dimensionResource(R.dimen.CardViewPadding)/2
                    ) { page ->
                        WeatherTimeItem(
                            modifier = Modifier.graphicsLayer {
                                val pageOffset =
                                    (pagerState.currentPage - page + pagerState.currentPageOffsetFraction)
                                val offsetFraction = pageOffset.absoluteValue.coerceIn(0f, 1f)

                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1.0f,
                                    fraction = 1f - offsetFraction,
                                )

                                scaleX = lerp(
                                    start = 1f,
                                    stop = 0.8f,
                                    fraction = offsetFraction,
                                )

                                scaleY = scaleX
                                translationX =
                                    size.width * (1 - scaleX) / 2 * (if (pagerState.currentPage > page) 1 else -1)
                            },
                            timeWeatherData = list[page]
                        )
                    }
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
fun WeatherTimeItem(modifier: Modifier = Modifier, timeWeatherData: TimeWeatherData) {
    val context = LocalContext.current

    val weatherImg = timeWeatherData.rain.weatherRainImgConvert()
    val imgDrawable = timeWeatherData.sky.skyImgEnum(weatherImg).imgConvert(context)

    val cardTimeWeatherImageSmall = dimensionResource(R.dimen.TimeItemImageSmallSize)
    val imgTextSpacer = dimensionResource(R.dimen.ItemPadding)

    val cardTextDateTime = dimensionResource(R.dimen.TimeItemTextDateTime).sp()
    val cardText = dimensionResource(R.dimen.TimeItemText).sp()

    val loadingImageText = stringResource(R.string.loadingImage)
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color_7192ad),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.TimeItemElevation)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.TimeItemInPadding)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = timeWeatherData.weatherDate.dateConvert(context),
                    fontSize = cardTextDateTime,
                    color = Color.White
                )
                Text(
                    text = timeWeatherData.weatherTime.timeDataConvert(context),
                    fontSize = cardTextDateTime,
                    color = Color.White
                )
                GlideImage(
                    model = imgDrawable,
                    contentDescription = loadingImageText,
                    modifier = Modifier.size(dimensionResource(R.dimen.TimeItemWeatherSize)),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            Column(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.TimeItemSpaceByPadding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeWeatherData.temp.tempConvert(context),
                    fontSize = cardText,
                    color = Color.White
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlideImage(
                        model = R.drawable.rainper,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(dimensionResource(R.dimen.TimeItemImageSmallSize)),
                        colorFilter = ColorFilter.tint(Color.White),
                    )
                    Spacer(modifier = Modifier.width(imgTextSpacer))
                    Text(
                        text = timeWeatherData.rainPer.rainPerConvert(context),
                        fontSize = cardText,
                        color = Color.White,
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlideImage(
                        model = R.drawable.waterper,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(cardTimeWeatherImageSmall),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Spacer(modifier = Modifier.width(imgTextSpacer))
                    Text(
                        text = timeWeatherData.wet.wetConvert(context),
                        fontSize = cardText,
                        color = Color.White,
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlideImage(
                        model = R.drawable.wind,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(cardTimeWeatherImageSmall),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Spacer(Modifier.width(imgTextSpacer))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = timeWeatherData.windDir.windDir(context),
                            fontSize = cardText,
                            color = Color.White,
                        )
                        Text(
                            text = timeWeatherData.windPower.windPower(context),
                            fontSize = dimensionResource(R.dimen.TimeItemWindText).sp(),
                            color = Color.White,
                        )
                    }
                }

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = timeWeatherData.rainMm.rainConvert(context),
                    fontSize = cardText,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun timeDataList(data: WeatherResponse): List<TimeWeatherData> {
    val weatherItem = data.response.body.items.item

    return weatherItem.groupBy { it.fcstDate to it.fcstTime }
        .map { (_, items) ->
            TimeWeatherData(
                weatherTime = items.first().fcstTime,
                weatherDate = items.first().fcstDate,
                temp = items.find { it.category == TMP_TIME }?.fcstValue ?: "",
                windDir = items.find { it.category == WIND_DIR }?.fcstValue ?: "",
                windPower = items.find { it.category == WIND_POWER }?.fcstValue ?: "",
                sky = items.find { it.category == SKY }?.fcstValue ?: "",
                rain = items.find { it.category == RAIN_TYPE }?.fcstValue ?: "",
                rainPer = items.find { it.category == RAIN_PER }?.fcstValue ?: "",
                rainMm = items.find { it.category == RAIN_MM }?.fcstValue ?: "",
                wet = items.find { it.category == WET }?.fcstValue ?: ""
            )
        }
}

@Preview
@Composable
fun PreviewCard(
    @PreviewParameter(CardDataPreviewParamProvider::class) weatherState: TimeWeatherData
) {
    WeatherTimeItem(timeWeatherData = weatherState)
}

class CardDataPreviewParamProvider : PreviewParameterProvider<TimeWeatherData> {
    override val values = sequenceOf(
        TimeWeatherData(
            weatherDate = "20240916",
            weatherTime = "1800",
            temp = "29",
            sky = "3",
            rainPer = "30",
            rainMm = "강수없음",
            wet = "70",
            windDir = "156",
            windPower = "1.3",
            rain = "0"
        )
    )
}