package com.android.sj.presentation.ui.compose.airQuality.measuringstation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.sj.presentation.models.uimodels.airquality.StationFindUIModel
import com.android.sj.presentation.state.viewstate.AirQualityViewState
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.ui.previewParam.AirQualityPreviewParamProvider
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.android.sj.presentation.utils.LocalAirQualityVM
import com.android.sj.presentation.utils.LocalLocationDataManager
import com.android.sj.presentation.utils.rltmTitle

@Composable
fun MeasuringStationColumnCommon(
    modifier: Modifier,
    onLoadStation: (String) -> Unit,
    errorFunc: (String, String) -> Unit
) {
    var dropdownSelectedOption by remember { mutableStateOf("통합 대기") }

    val airQualityState by LocalAirQualityVM.current.viewState.collectAsStateWithLifecycle()
    val locationDataManager = LocalLocationDataManager.current

    val locationData = locationDataManager.locationData.collectAsStateWithLifecycle()
    val locationValue = locationData.value

    UiStateHandler(modifier,
        state = airQualityState.stationFindUiState,
        errorFunc = { errorFunc(locationValue.x, locationValue.y) }) { successState ->
        StationFindSuccess(
            modifier,
            successState,
            dropdownSelectedOption,
            stationFindErrorFunc = {
                if (successState.stationName != "정보없음") {
                    onLoadStation(successState.stationName)
                }
            },
            onOptionSelected = {
                dropdownSelectedOption = it
            }
        )
    }
}


@Composable
fun StationFindSuccess(
    modifier: Modifier,
    stationFindState: StationFindUIModel,
    dropdownSelectedOption: String,
    stationFindErrorFunc:  () -> Unit,
    onOptionSelected: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stationFindState.stationName.rltmTitle(context),
            style = defaultTitleTextStyle()
        )

        HandleRltmStationState(modifier, dropdownSelectedOption, onOptionSelected) { stationFindErrorFunc() }
    }
}

@Composable
fun HandleRltmStationState(
    modifier: Modifier,
    dropdownSelectedOption: String,
    onOptionSelected: (String) -> Unit,
    errorFunc: () -> Unit
) {
    val airQualityState by LocalAirQualityVM.current.viewState.collectAsStateWithLifecycle()

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        UiStateHandler(modifier, airQualityState.rltmStationUiState, errorFunc = { errorFunc() }) { successState ->
            Column(modifier = Modifier.weight(1f)) {
                CustomSpinner(onOptionSelected)
                Text(
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 10.sp,
                    text = successState.dataTime
                )
            }
            Spacer(Modifier.width(3.dp))
            MeasuringStationCard(
                modifier = Modifier.weight(1f),
                dropdownSelectedOption = dropdownSelectedOption,
                data = successState
            )
        }
    }
}

@Preview
@Composable
fun PreviewMeasuringStationColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: AirQualityViewState) {
    MeasuringStationColumnCommon(
        modifier = Modifier.height(200.dp),
        onLoadStation = {},
        errorFunc = { _, _ ->}
    )
}