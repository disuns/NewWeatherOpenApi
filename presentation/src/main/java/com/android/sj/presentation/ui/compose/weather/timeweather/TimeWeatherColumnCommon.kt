package com.android.sj.presentation.ui.compose.weather.timeweather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.sj.presentation.R
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.utils.LocalLocationDataManager
import com.android.sj.presentation.utils.LocalWeatherVM
import com.android.sj.presentation.utils.sp
import kotlin.math.absoluteValue

@Composable
fun TimeWeatherColumnCommon(
    modifier: Modifier,
    errorFunc: (String, String) -> Unit
) {
    val weatherState by LocalWeatherVM.current.viewState.collectAsStateWithLifecycle()

    val locationDataManager = LocalLocationDataManager.current

    val locationValue by locationDataManager.locationData.collectAsStateWithLifecycle()

    UiStateHandler(modifier, weatherState.timeWeatherUiState, errorFunc = {errorFunc(locationValue.lat.toString(), locationValue.lng.toString())}) { successState ->
        val list = successState.items
        val pagerState = rememberPagerState(
            pageCount = { list.size }
        )
        Column(
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.timeWeather),
                fontSize = dimensionResource(R.dimen.WeatherViewTitle).sp(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                state = pagerState,
                contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.WeatherCardViewPadding)),
                pageSpacing = dimensionResource(R.dimen.WeatherCardViewPadding) / 2
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

                        val scales = lerp(
                            start = 1f,
                            stop = 0.7f,
                            fraction = offsetFraction,
                        )
                        scaleY = scales
                        scaleX = scales
                        translationX =
                            size.width * (1 - scaleX) / 2 * (if (pagerState.currentPage > page) 1 else -1)
                    },
                    timeWeatherData = list[page]
                )
            }
        }
    }
}
