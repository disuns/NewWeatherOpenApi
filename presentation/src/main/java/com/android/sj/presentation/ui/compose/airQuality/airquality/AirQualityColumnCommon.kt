package com.android.sj.presentation.ui.compose.airQuality.airquality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.sj.presentation.R
import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.state.viewstate.AirQualityViewState
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.ui.previewParam.AirQualityPreviewParamProvider
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.android.sj.presentation.utils.LocalAirQualityVM
import com.android.sj.presentation.utils.actionKnact
import com.android.sj.presentation.utils.sp

@Composable
fun AirQualityColumnCommon(
    modifier: Modifier,
    errorFunc: () -> Unit
) {
    val airQualityState by LocalAirQualityVM.current.viewState.collectAsStateWithLifecycle()

    UiStateHandler(modifier, airQualityState.airQualityUiState, errorFunc = errorFunc) { successState ->
        AirQualityColumnContent(
            successState = successState,
            modifier = modifier
        )
    }
}

@Composable
private fun AirQualityColumnContent(
    successState: AirQualityUiModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier.wrapContentHeight()) {
        Text(
            text = stringResource(R.string.airQualityTitle),
            style = defaultTitleTextStyle(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = successState.dataTimeAndCode,
            fontSize = dimensionResource(R.dimen.AirQualityDateCode).sp(),
            modifier = Modifier.align(Alignment.End)
        )

        Text(
            text = successState.overall,
            fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
            modifier = Modifier.align(Alignment.Start)
        )

        Text(
            text = successState.cause,
            fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
            modifier = Modifier.align(Alignment.Start)
        )

        val actionText = successState.actionKnack
            .takeIf { !it.isNullOrBlank() }
            ?: stringResource(R.string.nullString)

        Text(
            text = actionText.actionKnact(context),
            fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
            modifier = Modifier.align(Alignment.Start)
        )

        Text(
            text = stringResource(R.string.nationalFineDust),
            fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        LazyColumn {
            items(successState.informGrades.chunked(3)) { rowGrades ->
                Row {
                    rowGrades.forEach { grade ->
                        val statusColor by remember(grade) {
                            derivedStateOf {
                                when {
                                    "좋음" in grade -> Color.Green
                                    "나쁨" in grade -> Color.Red
                                    else            -> Color.Gray
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)) {
                            Text(
                                text = grade
                                    .replace("좋음", "")
                                    .replace("나쁨", "")
                                    .replace("보통", "")
                            )
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(color = statusColor, shape = CircleShape)
                            )
                        }
                    }
                }
            }
        }
        //테스트중(아래는 테스트 전 코드)
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(min = 0.dp, max = 500.dp)
//                .padding(vertical = 8.dp)
//        ) {
//            items(successState.informGrades.size) { index ->
//                val grade = successState.informGrades[index]
//                val statusColor by remember(grade) {
//                    derivedStateOf {
//                        when {
//                            "좋음" in grade -> Color.Green
//                            "나쁨" in grade -> Color.Red
//                            else            -> Color.Gray
//                        }
//                    }
//                }
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = grade
//                            .replace("좋음", "")
//                            .replace("나쁨", "")
//                            .replace("보통", "")
//                    )
//                    Box(
//                        modifier = Modifier
//                            .size(10.dp)
//                            .background(color = statusColor, shape = CircleShape)
//                    )
//                }
//            }
//        }
    }
}

@Preview
@Composable
fun PreviewAirQualityColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: AirQualityViewState) {
    val modifier = Modifier.background(Color.White)
    val successState = previewData.airQualityUiState.model!!

    AirQualityColumnContent(successState, modifier)
}
