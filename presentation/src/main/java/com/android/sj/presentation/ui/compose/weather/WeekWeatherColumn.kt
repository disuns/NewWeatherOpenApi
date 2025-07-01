package com.android.sj.presentation.ui.compose.weather

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.android.sj.presentation.R
import com.android.sj.presentation.models.state.viewstate.WeatherViewState
import com.android.sj.presentation.models.uimodels.weather.WeekRainSkyUIModel
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.ui.previewParam.WeekCardDataPreviewParamProvider
import com.android.sj.presentation.ui.theme.Color_eceff1
import com.android.sj.presentation.utils.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun WeekWeatherColumn(
    modifier: Modifier,
    weatherState: WeatherViewState,
    errorFunc: () -> Unit
) {
    UiStateHandler(modifier, weatherState.weekRainSkyUiState, errorFunc = {errorFunc()}) { successState ->
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
                items(successState.items.size) { item ->
                    if(successState.items[item].skyAm != null && successState.items[item].skyPm != null)
                        WeekWeatherItem(successState.items[item])
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeekWeatherItem(data: WeekRainSkyUIModel.Item) {
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
                text = data.weekDate,
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
                        text = data.rainAm,
                        fontSize = textSize
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = data.skyAm.toString(),
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
                        text = data.rainPm,
                        fontSize = textSize
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = data.skyPm.toString(),
                        fontSize = textSize
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWeekCard(
    @PreviewParameter(WeekCardDataPreviewParamProvider::class) weatherState: WeekRainSkyUIModel.Item
) {
    WeekWeatherItem(data = weatherState)
}