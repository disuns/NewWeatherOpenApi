package com.android.sj.presentation.ui.compose.airQuality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.R
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.models.airquality.RltmStationUIData
import com.android.sj.presentation.models.airquality.RltmStationUIData.MeasuringData
import com.android.sj.presentation.models.airquality.StationFindUIData
import com.android.sj.presentation.state.AirQualityViewState
import com.android.sj.presentation.ui.compose.common.ApiResultHandler
import com.android.sj.presentation.ui.theme.Color_F0FFF0
import com.android.sj.presentation.ui.theme.Color_ffd700
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.android.sj.presentation.utils.rltmFlag
import com.android.sj.presentation.utils.rltmGradeConvert
import com.android.sj.presentation.utils.rltmTitle
import com.android.sj.presentation.utils.rltmValueConvert
import com.android.sj.presentation.viewmodels.AirQualityViewModel

@Composable
fun MeasuringStationColumn(
    modifier: Modifier,
    airQualityState: AirQualityViewState,
    viewModel: AirQualityViewModel,
    errorFunc: () -> Unit
) {
    var dropdownSelectedOption by remember { mutableStateOf("통합 대기") }

    ApiResultHandler(modifier, airQualityState.stationFindState, errorFunc = { errorFunc() }) { succesState ->
        StationFindSuccess(
            modifier,
            succesState,
            airQualityState,
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
    stationFindState: ApiResult.Success<StationFindUIData>,
    airQualityState: AirQualityViewState,
    dropdownSelectedOption: String,
    viewModel: AirQualityViewModel,
    onOptionSelected: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stationFindState.value.stationName,
            style = defaultTitleTextStyle()
        )

        HandleRltmStationState(
            airQualityState.rltmStationState, modifier, dropdownSelectedOption, onOptionSelected
        ) {
            if(stationFindState.value.stationName != "정보없음"){
                viewModel.handleIntent(AirQualityIntent.LoadRltmStation(stationFindState.value.stationName.rltmTitle(context)))
            }
        }
    }
}

@Composable
fun HandleRltmStationState(
    rltmStationState: ApiResult<RltmStationUIData>,
    modifier: Modifier,
    dropdownSelectedOption: String,
    onOptionSelected: (String) -> Unit,
    errorFunc: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ApiResultHandler(modifier, rltmStationState, errorFunc = { errorFunc() }) { successState ->
            Column(modifier = Modifier.weight(1f)) {
                CustomSpinner(onOptionSelected)
                Text(
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 10.sp,
                    text = successState.value.dataTime
                )
            }
            Spacer(Modifier.width(3.dp))
            MeasuringStationCard(
                modifier = Modifier.weight(1f),
                dropdownSelectedOption = dropdownSelectedOption,
                data = successState.value
            )
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
    data: RltmStationUIData
) {
    val context = LocalContext.current

    val rltmData = stringArrayResource(R.array.rltmData)
    val dataMapping = rltmData.indices.associate { rltmData[it] to data.measuringData[it] }
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

//@Preview
//@Composable
//fun PreviewMeasuringStationColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: AirQualityViewState) {
//    val airQualityService = FakeAirQualityService()
//    val airQualityRepository = AirQualityRepository(airQualityService)
//    val airQualityViewModel = AirQualityViewModel(repository = airQualityRepository)
//
//    MeasuringStationColumn(
//        modifier = Modifier.height(200.dp),
//        airQualityState = previewData,
//        viewModel = airQualityViewModel,
//        errorFunc = {}
//    )
//}