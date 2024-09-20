package com.project.newweatheropenapi.ui.compose.airQuality

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.ui.compose.common.ApiResultHandler
import com.project.newweatheropenapi.ui.compose.common.DefaultError
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.actionKnact
import com.project.newweatheropenapi.utils.airDateAndCode
import com.project.newweatheropenapi.utils.airInformGrade
import com.project.newweatheropenapi.utils.dataPotalResultCode
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.sp

@Composable
fun PredictionModelColumn(modifier: Modifier, airQualityState: ApiResult<AirQualityResponse>){
    val context = LocalContext.current

    ApiResultHandler(modifier,airQualityState){ successState ->
        if(successState.value.response.header.resultCode!= NO_ERROR) {
            DefaultError(modifier)
            successState.value.response.header.resultCode.dataPotalResultCode(context)
        }else{
            val data = successState.value.response.body.items[0]
            Column(modifier = modifier) {
                Text(text = stringResource(R.string.airQualityTitle),
                    fontSize = dimensionResource(R.dimen.AirQualityTitle).sp(),
                    fontWeight = FontWeight.Bold,
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

                logMessage(informGrades)
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    items(informGrades.size) { item ->
                        Text(text = item.toString())
                    }
                }
            }
        }

    }
}