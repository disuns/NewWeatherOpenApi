package com.android.sj.presentation.ui.compose.airQuality

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.android.sj.presentation.R
import com.android.sj.presentation.models.state.AirQualityViewState
import com.android.sj.presentation.ui.compose.common.ApiResultHandler
import com.android.sj.presentation.ui.previewParam.AirQualityPreviewParamProvider
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.android.sj.presentation.utils.actionKnact
import com.android.sj.presentation.utils.sp

@Composable
fun AirQualityColumn(
    modifier: Modifier,
    airQualityState: AirQualityViewState,
    errorFunc: () -> Unit
) {
    val context = LocalContext.current

    ApiResultHandler(modifier, airQualityState.airQualityState, errorFunc = errorFunc) { successState ->
        val data = successState.value
        Column(modifier = modifier.wrapContentHeight()) {
            Text(
                text = stringResource(R.string.airQualityTitle),
                style = defaultTitleTextStyle(),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = data.dataTimeAndCode,
                fontSize = dimensionResource(R.dimen.AirQualityDateCode).sp(),
                modifier = Modifier.align(Alignment.End)
            )

            Text(
                text = data.overall,
                fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = data.cause,
                fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
                modifier = Modifier.align(Alignment.Start)
            )

            val actionKnacktNullCheck = when (data.actionKnack.isNullOrBlank()) {
                true -> stringResource(R.string.nullString)
                else -> data.actionKnack
            }
            Text(
                text = actionKnacktNullCheck.actionKnact(context),
                fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = stringResource(R.string.nationalFineDust),
                fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = 500.dp)
                    .padding(vertical = 8.dp)
            ) {
                val informGrades = data.informGrades
                items(informGrades.size) { item ->
                    val statusColor = when {
                        informGrades[item].contains("좋음") -> Color.Green
                        informGrades[item].contains("나쁨") -> Color.Red
                        else -> Color.Gray
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = informGrades[item].replace("좋음", "").replace("나쁨", "").replace("보통", "")
                        )
                        Canvas(modifier = Modifier.size(10.dp)
                        ) { drawCircle(color = statusColor)}
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAirQualityColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: AirQualityViewState) {
    AirQualityColumn(
        modifier = Modifier.background(Color.White),
        airQualityState = previewData,
        errorFunc = {}
    )
}
