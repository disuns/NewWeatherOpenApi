package com.project.newweatheropenapi.ui.compose.airQuality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.ui.compose.common.ApiResultHandler
import com.project.newweatheropenapi.ui.compose.common.DefaultError
import com.project.newweatheropenapi.ui.previewParamProvider.AirQualityPreviewParamProvider
import com.project.newweatheropenapi.ui.theme.defaultTitleTextStyle
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.actionKnact
import com.project.newweatheropenapi.utils.airDateAndCode
import com.project.newweatheropenapi.utils.airInformGrade
import com.project.newweatheropenapi.utils.dataPotalResultCode
import com.project.newweatheropenapi.utils.sp

@Composable
fun AirQualityColumn(modifier: Modifier, airQualityState: ApiResult<AirQualityResponse>){
    val context = LocalContext.current

    ApiResultHandler(modifier,airQualityState){ successState ->
        if (successState.value.response.header.resultCode != NO_ERROR) {
            DefaultError(modifier)
            successState.value.response.header.resultCode.dataPotalResultCode(context)
        } else {
            val data = successState.value.response.body.items[0]
            Column(modifier = modifier.wrapContentHeight()) {
                Text(text = stringResource(R.string.airQualityTitle),
                    style = defaultTitleTextStyle(),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(text = data.informCode.airDateAndCode(data.dataTime, context),
                    fontSize = dimensionResource(R.dimen.AirQualityDateCode).sp(),
                    modifier = Modifier.align(Alignment.End)
                )

                Text(text = data.informOverall,
                    fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
                    modifier = Modifier.align(Alignment.Start)
                )

                Text(text = data.informCause,
                    fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
                    modifier = Modifier.align(Alignment.Start)
                )

                val actionKnacktNullCheck =when(data.actionKnack.isNullOrBlank()){
                    true-> stringResource(R.string.nullString)
                    else->data.actionKnack
                }
                Text(text = actionKnacktNullCheck.actionKnact(context),
                    fontSize = dimensionResource(R.dimen.AirQualityCauseAndOverAll).sp(),
                    modifier = Modifier.align(Alignment.Start)
                )

                val informGrades = data.informGrade.airInformGrade().toMutableList()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(min = 0.dp, max = 500.dp)
                        .padding(vertical = 8.dp)
                ) {
                    items(informGrades.size) { item ->
                        Text(text = informGrades[item])
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAirQualityColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: ApiResult<AirQualityResponse>) {
    AirQualityColumn(
        modifier = Modifier.background(Color.White),
        airQualityState = previewData
    )
}
