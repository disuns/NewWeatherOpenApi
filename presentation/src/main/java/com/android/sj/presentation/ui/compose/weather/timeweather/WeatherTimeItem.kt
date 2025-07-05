package com.android.sj.presentation.ui.compose.weather.timeweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.android.sj.presentation.R
import com.android.sj.presentation.models.uimodels.weather.TimeWeatherUIModel
import com.android.sj.presentation.ui.previewParam.CardDataPreviewParamProvider
import com.android.sj.presentation.ui.theme.Color_eceff1
import com.android.sj.presentation.utils.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherTimeItem(modifier: Modifier = Modifier, timeWeatherData: TimeWeatherUIModel.Item) {
    val cardTimeWeatherImageSmall = dimensionResource(R.dimen.TimeItemImageSmallSize)
    val imgTextSpacer = dimensionResource(R.dimen.ItemPadding)

    val cardTextDateTime = dimensionResource(R.dimen.TimeItemTextDateTime).sp()
    val cardText = dimensionResource(R.dimen.TimeItemText).sp()

    val loadingImageText = stringResource(R.string.loadingImage)
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color_eceff1),
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
                    text = timeWeatherData.weatherDate,
                    fontSize = cardTextDateTime
                )
                Text(
                    text = timeWeatherData.weatherTime,
                    fontSize = cardTextDateTime
                )
                GlideImage(
                    model = timeWeatherData.imgDrawable,
                    contentDescription = loadingImageText,
                    modifier = Modifier.size(dimensionResource(R.dimen.TimeItemWeatherSize)),
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }

            Column(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.TimeItemSpaceByPadding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeWeatherData.temp,
                    fontSize = cardText
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlideImage(
                        model = R.drawable.rainper,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(dimensionResource(R.dimen.TimeItemImageSmallSize)),
                        colorFilter = ColorFilter.tint(Color.Black),
                    )
                    Spacer(modifier = Modifier.width(imgTextSpacer))
                    Text(
                        text = timeWeatherData.rainPer,
                        fontSize = cardText
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlideImage(
                        model = R.drawable.waterper,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(cardTimeWeatherImageSmall),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Spacer(modifier = Modifier.width(imgTextSpacer))
                    Text(
                        text = timeWeatherData.wet,
                        fontSize = cardText
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlideImage(
                        model = R.drawable.wind,
                        contentDescription = loadingImageText,
                        modifier = Modifier.size(cardTimeWeatherImageSmall),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Spacer(Modifier.width(imgTextSpacer))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = timeWeatherData.windDir,
                            fontSize = cardText
                        )
                        Text(
                            text = timeWeatherData.windPower,
                            fontSize = dimensionResource(R.dimen.TimeItemWindText).sp()
                        )
                    }
                }

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = timeWeatherData.rainMm,
                    fontSize = cardText
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCard(
    @PreviewParameter(CardDataPreviewParamProvider::class) weatherState: TimeWeatherUIModel.Item
) {
    WeatherTimeItem(timeWeatherData = weatherState)
}