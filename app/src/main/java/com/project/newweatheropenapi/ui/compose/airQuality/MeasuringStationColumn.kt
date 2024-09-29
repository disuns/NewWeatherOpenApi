package com.project.newweatheropenapi.ui.compose.airQuality

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.dataclass.MeasuringData
import com.project.newweatheropenapi.dataclass.state.AirQualityViewState
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.RltmStationResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.StationFindResponse
import com.project.newweatheropenapi.network.repository.AirQualityRepository
import com.project.newweatheropenapi.sealed.intent.AirQualityIntent
import com.project.newweatheropenapi.ui.compose.common.ApiResultHandler
import com.project.newweatheropenapi.ui.compose.common.DataPotalSuccesError
import com.project.newweatheropenapi.ui.previewParamAndService.AirQualityPreviewParamProvider
import com.project.newweatheropenapi.ui.previewParamAndService.FakeAirQualityService
import com.project.newweatheropenapi.ui.theme.Color_F0FFF0
import com.project.newweatheropenapi.ui.theme.Color_ffd700
import com.project.newweatheropenapi.ui.theme.defaultTitleTextStyle
import com.project.newweatheropenapi.utils.NO_ERROR
import com.project.newweatheropenapi.utils.dataPotalResultCode
import com.project.newweatheropenapi.utils.rltmFlag
import com.project.newweatheropenapi.utils.rltmGradeConvert
import com.project.newweatheropenapi.utils.rltmStationDate
import com.project.newweatheropenapi.utils.rltmTitle
import com.project.newweatheropenapi.utils.rltmValueConvert
import com.project.newweatheropenapi.viewmodel.AirQualityViewModel

@Composable
fun MeasuringStationColumn(
    modifier: Modifier,
    airQualityState: AirQualityViewState,
    viewModel: AirQualityViewModel,
    errorFunc: () -> Unit
) {
    val context = LocalContext.current
    var dropdownSelectedOption by remember { mutableStateOf("통합 대기") }

    ApiResultHandler(modifier, airQualityState.stationFindState, errorFunc = { errorFunc() }) { succesState ->
        StationFindSuccess(
            modifier,
            succesState,
            airQualityState,
            context,
            dropdownSelectedOption,
            viewModel,
            onOptionSelected = {
                dropdownSelectedOption = it
            }
        )
    }
}


@Composable
fun StationFindSuccess(
    modifier: Modifier,
    stationFindState: ApiResult.Success<StationFindResponse>,
    airQualityState: AirQualityViewState,
    context: Context,
    dropdownSelectedOption: String,
    viewModel: AirQualityViewModel,
    onOptionSelected: (String) -> Unit
) {
    if (stationFindState.value.response.header.resultCode != NO_ERROR) {
        DataPotalSuccesError(
            modifier,
            stationFindState.value.response.header.resultCode.dataPotalResultCode(context)
        )
    } else {
        val stationItems = stationFindState.value.response.body?.items
        val stationName = stationItems?.firstOrNull()?.stationName
        val displayText = stationName?.rltmTitle(context) ?: "정보없음"

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = displayText,
                style = defaultTitleTextStyle()
            )

            HandleRltmStationState(
                airQualityState.rltmStationState, modifier, context, dropdownSelectedOption, onOptionSelected
            ) {
                stationItems?.firstOrNull()?.let { station ->
                    viewModel.handleIntent(AirQualityIntent.LoadRltmStation(station.stationName))
                }
            }
        }
    }
}

@Composable
fun HandleRltmStationState(
    rltmStationState: ApiResult<RltmStationResponse>,
    modifier: Modifier,
    context: Context,
    dropdownSelectedOption: String,
    onOptionSelected: (String) -> Unit,
    errorFunc: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ApiResultHandler(modifier, rltmStationState, errorFunc = { errorFunc() }) { successState ->
            if (successState.value.response.header.resultCode != NO_ERROR) {
                DataPotalSuccesError(
                    modifier,
                    successState.value.response.header.resultCode.dataPotalResultCode(context)
                )
            } else {
                Column(modifier = Modifier.weight(1f)) {
                    CustomSpinner(onOptionSelected)
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        fontSize = 10.sp,
                        text = successState.value.response.body.items[0].dataTime.rltmStationDate(
                            context
                        )
                    )
                }
                Spacer(Modifier.width(3.dp))
                MeasuringStationCard(
                    modifier = Modifier.weight(1f),
                    dropdownSelectedOption = dropdownSelectedOption,
                    data = successState.value.response.body.items[0]
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSpinner(selectOption: (String) -> Unit) {
    var expandStatus by remember { mutableStateOf(false) }
    val rltmData = stringArrayResource(R.array.rltmData)
    var selectedOption by remember { mutableStateOf(rltmData[0]) }


    ExposedDropdownMenuBox(
        expanded = expandStatus,
        onExpandedChange = { expandStatus = !expandStatus },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape)))
            .background(
                color = Color_ffd700,
                shape = RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape))
            )
    ) {
        TextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandStatus) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            shape = RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape)),
            containerColor = Color_ffd700,
            expanded = expandStatus,
            onDismissRequest = { expandStatus = false }) {
            rltmData.forEachIndexed { _, data ->
                DropdownMenuItem(
                    text = { Text(text = data) },
                    onClick = {
                        selectedOption = data
                        selectOption(selectedOption)
                        expandStatus = false
                    }
                )
            }
        }
    }
}

@Composable
fun MeasuringStationCard(
    modifier: Modifier,
    dropdownSelectedOption: String,
    data: RltmStationResponse.Response.Body.Item
) {
    val context = LocalContext.current

    val rltmData = stringArrayResource(R.array.rltmData)

    val dataMapping = mapOf(
        rltmData[0] to MeasuringData(data.khaiValue, data.khaiGrade, null),
        rltmData[1] to MeasuringData(data.pm25Value, data.pm25Grade, data.pm25Flag),
        rltmData[2] to MeasuringData(data.pm10Value, data.pm10Grade, data.pm10Flag),
        rltmData[3] to MeasuringData(data.o3Value, data.o3Grade, data.o3Flag),
        rltmData[4] to MeasuringData(data.coValue, data.coGrade, data.coFlag),
        rltmData[5] to MeasuringData(data.no2Value, data.no2Grade, data.no2Flag),
        rltmData[6] to MeasuringData(data.so2Value, data.so2Grade, data.so2Flag)
    )

    val stationData = dataMapping[dropdownSelectedOption] ?: MeasuringData("", "", null)

    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.rltmStationCardPadding)),
        colors = cardColors(
            containerColor = Color_F0FFF0
        ),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.TimeItemElevation)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.rltmStationCardPadding))
        ) {
            Text(
                text = (stationData.data1?.takeIf { it.isNotBlank() }
                    ?: stringResource(R.string.nullString))
                    .rltmValueConvert(rltmData.indexOf(dropdownSelectedOption), context),
                fontSize = 16.sp
            )
            Text(
                text = (stationData.data2?.takeIf { it.isNotBlank() }
                    ?: stringResource(R.string.nullString)).rltmGradeConvert(context),
                fontSize = 16.sp
            )
            Text(
                text = (stationData.data3?.takeIf { it.isNotBlank() }
                    ?: stringResource(R.string.nullString)).rltmFlag(context),
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewMeasuringStationColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: AirQualityViewState) {
    val airQualityService = FakeAirQualityService()
    val airQualityRepository = AirQualityRepository(airQualityService)
    val airQualityViewModel = AirQualityViewModel(repository = airQualityRepository)

    MeasuringStationColumn(
        modifier = Modifier.height(200.dp),
        airQualityState = previewData,
        viewModel = airQualityViewModel,
        errorFunc = {}
    )
}