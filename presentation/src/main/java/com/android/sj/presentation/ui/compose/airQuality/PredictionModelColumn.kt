package com.android.sj.presentation.ui.compose.airQuality

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.android.sj.presentation.R
import com.android.sj.presentation.models.state.viewstate.AirQualityViewState
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.ui.previewParam.AirQualityPreviewParamProvider
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PredictionModelColumn(
    modifier: Modifier,
    airQualityState: AirQualityViewState,
    errorFunc: () -> Unit
) {
    Column(modifier = modifier.padding(top = 8.dp)) {
        UiStateHandler(modifier, airQualityState.airQualityUiState, errorFunc = { errorFunc() }) { successState ->

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.predictionModel),
                style = defaultTitleTextStyle()
            )

            val imageList = listOf(
                successState.imageUrl1,
                successState.imageUrl2,
                successState.imageUrl3
            )

            val pagerState = rememberPagerState(
                pageCount = { imageList.size }
            )


            HorizontalPager(
                modifier = Modifier,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.PredictionModelCardViewPadding)),
                pageSpacing = dimensionResource(R.dimen.PredictionModelCardViewPadding) / 2
            ) { page ->
                GlideImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .graphicsLayer {
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
                    model = imageList[page],
                    contentDescription = stringResource(R.string.loadingImage)
                )
            }

            Text(text = stringResource(R.string.airQualityDesc))
        }
    }
}

@Preview
@Composable
fun PreviewPredictionModelColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: AirQualityViewState) {
    PredictionModelColumn(
        modifier = Modifier.height(900.dp),
        airQualityState = previewData,
        errorFunc = {}
    )
}