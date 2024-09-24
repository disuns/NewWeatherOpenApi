package com.project.newweatheropenapi.ui.compose.airQuality

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.ui.compose.common.ApiResultHandler
import com.project.newweatheropenapi.ui.compose.common.DataPotalSuccesError
import com.project.newweatheropenapi.ui.previewParamAndService.AirQualityPreviewParamProvider
import com.project.newweatheropenapi.ui.theme.defaultTitleTextStyle
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.dataPotalResultCode
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PredictionModelColumn(
    modifier: Modifier,
    airQualityState: ApiResult<AirQualityResponse>,
    errorFunc: () -> Unit
) {
    val context = LocalContext.current

    Column(modifier = modifier.padding(top = 8.dp)) {
        ApiResultHandler(modifier, airQualityState, errorFunc = { errorFunc() }) { successState ->
            if (successState.value.response.header.resultCode != NO_ERROR) {
                DataPotalSuccesError(modifier, successState.value.response.header.resultCode.dataPotalResultCode(context))
            } else {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.predictionModel),
                    style = defaultTitleTextStyle()
                )

                val airData = successState.value.response.body.items[0]

                val imageList = mutableListOf(
                    airData.imageUrl1,
                    airData.imageUrl2,
                    airData.imageUrl3
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
}

@Preview
@Composable
fun PreviewPredictionModelColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: ApiResult<AirQualityResponse>) {
    PredictionModelColumn(
        modifier = Modifier.height(900.dp),
        airQualityState = previewData,
        errorFunc = {}
    )
}