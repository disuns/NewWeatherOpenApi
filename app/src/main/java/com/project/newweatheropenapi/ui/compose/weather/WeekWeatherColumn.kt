package com.project.newweatheropenapi.ui.compose.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.dataclass.WeekDate
import com.project.newweatheropenapi.dataclass.WeekWeatherData
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeekRainSkyResponse
import com.project.newweatheropenapi.ui.compose.common.ApiResultHandler
import com.project.newweatheropenapi.ui.compose.common.DataPotalSuccesError
import com.project.newweatheropenapi.ui.previewParamAndService.WeekCardDataPreviewParamProvider
import com.project.newweatheropenapi.ui.theme.Color_eceff1
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.dataPotalResultCode
import com.project.newweatheropenapi.utils.managers.TimeManager
import com.project.newweatheropenapi.utils.rainPerConvert
import com.project.newweatheropenapi.utils.sp
import com.project.newweatheropenapi.utils.weekDateConvert

@Composable
fun WeekWeatherColumn(
    modifier: Modifier,
    weekRainSkyState: ApiResult<WeekRainSkyResponse>,
    errorFunc: () -> Unit
) {
    val context = LocalContext.current

    ApiResultHandler(modifier, weekRainSkyState, errorFunc = {errorFunc()}) { successState ->
        if (successState.value.response.header.resultCode != NO_ERROR) {
            DataPotalSuccesError(modifier, successState.value.response.header.resultCode.dataPotalResultCode(context))
        } else {
            val list = weekDataList(successState.value)
            Column(
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weekWeather),
                    fontSize = dimensionResource(R.dimen.WeatherViewTitle).sp(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    items(list.size) { item ->
                        WeekWeatherItem(list[item])
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeekWeatherItem(data: WeekWeatherData) {
    val context = LocalContext.current

    val imageSize = dimensionResource(R.dimen.WeekItemImageSmall)
    val textSize = dimensionResource(R.dimen.WeekItemText).sp()

    val loadingImageText = stringResource(R.string.loadingImage)

    Card(
        modifier = Modifier.fillMaxWidth().padding(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color_eceff1),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.WeekItemElevation)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape))
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.WeekItemPadding)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(3f),
                text = data.weekDate.weekDateConvert(context),
                fontSize = textSize,
                textAlign = TextAlign.Center
            )
            Column(modifier = Modifier.weight(7f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.am),
                        fontSize = textSize
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    GlideImage(
                        model = R.drawable.rainper,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(imageSize),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = data.rainAm.rainPerConvert(context),
                        fontSize = textSize
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = data.skyAm,
                        fontSize = textSize
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.pm),
                        fontSize = textSize
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                    GlideImage(
                        model = R.drawable.rainper,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(imageSize),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = data.rainPm.rainPerConvert(context),
                        fontSize = textSize
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = data.skyPm,
                        fontSize = textSize
                    )
                }
            }
        }
    }
}

@Composable
fun weekDataList(data: WeekRainSkyResponse): List<WeekWeatherData> {
    val weatherItem = data.response.body.items.item
    val context = LocalContext.current
    val timeManager = TimeManager(context = context)

    return weatherItem.flatMap {
        mutableListOf(
            setWeekWeatherData(
                timeManager.getWeatherWeekUIDate(3),
                it.rnSt3Am.toString(),
                it.rnSt3Pm.toString(),
                it.wf3Am,
                it.wf3Pm
            ),
            setWeekWeatherData(
                timeManager.getWeatherWeekUIDate(4),
                it.rnSt4Am.toString(),
                it.rnSt4Pm.toString(),
                it.wf4Am,
                it.wf4Pm
            ),
            setWeekWeatherData(
                timeManager.getWeatherWeekUIDate(5),
                it.rnSt5Am.toString(),
                it.rnSt5Pm.toString(),
                it.wf5Am,
                it.wf5Pm
            ),
            setWeekWeatherData(
                timeManager.getWeatherWeekUIDate(6),
                it.rnSt6Am.toString(),
                it.rnSt6Pm.toString(),
                it.wf6Am,
                it.wf6Pm
            ),
            setWeekWeatherData(
                timeManager.getWeatherWeekUIDate(7),
                it.rnSt7Am.toString(),
                it.rnSt7Pm.toString(),
                it.wf7Am,
                it.wf7Pm
            )
        )
    }
}

private fun setWeekWeatherData(
    weekDate: WeekDate,
    rainAM: String,
    rainPM: String,
    skyAM: String,
    skyPM: String
): WeekWeatherData {
    return WeekWeatherData().apply {
        this.weekDate = weekDate
        this.rainAm = rainAM
        this.rainPm = rainPM
        this.skyAm = skyAM
        this.skyPm = skyPM
    }
}


@Preview
@Composable
fun PreviewWeekCard(
    @PreviewParameter(WeekCardDataPreviewParamProvider::class) weatherState: WeekWeatherData
) {
    WeekWeatherItem(data = weatherState)
}