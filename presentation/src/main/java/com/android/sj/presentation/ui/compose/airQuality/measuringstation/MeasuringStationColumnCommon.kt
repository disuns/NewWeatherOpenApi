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
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.state.viewstate.AirQualityViewState
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.ui.previewParam.AirQualityPreviewParamProvider
import com.android.sj.presentation.ui.theme.defaultTitleTextStyle
import com.android.sj.presentation.utils.LocalAirQualityVM
import com.android.sj.presentation.utils.LocalLocationDataManager
import com.android.sj.presentation.utils.rltmTitle

@Composable
fun MeasuringStationColumnCommon(
    modifier: Modifier = Modifier,
    onLoadStation: (String) -> Unit,
    errorFunc: (String, String) -> Unit
) {
    var dropdownSelectedOption by remember { mutableStateOf("통합 대기") }
    val airQualityState by LocalAirQualityVM.current.viewState.collectAsStateWithLifecycle()
    val locationData by LocalLocationDataManager.current.locationData.collectAsStateWithLifecycle()

    UiStateHandler(
        modifier,
        state = airQualityState.stationFindUiState,
        errorFunc = { errorFunc(locationData.x, locationData.y) }
    ) { stationFindState ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            StationColumnHeader(
                stationName = stationFindState.stationName
            )

            Spacer(modifier = Modifier.height(4.dp))

            UiStateHandler(
                modifier,
                state = airQualityState.rltmStationUiState,
                errorFunc = { if (stationFindState.stationName != "정보없음") {
                    onLoadStation(stationFindState.stationName)
                } }
            ) { rltmState ->
                StationColumnBody(
                    dropdownSelectedOption = dropdownSelectedOption,
                    data = rltmState,
                    onOptionSelected = { dropdownSelectedOption = it }
                )
            }
        }
    }
}

@Composable
private fun StationColumnHeader(
    stationName: String
) {
    val context = LocalContext.current
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = stationName.rltmTitle(context),
        style = defaultTitleTextStyle()
    )
}

@Composable
private fun StationColumnBody(
    dropdownSelectedOption: String,
    data: RltmStationUIModel,
    onOptionSelected: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            CustomSpinner(onOptionSelected)
            Text(
                modifier = Modifier.align(Alignment.End),
                fontSize = 10.sp,
                text = data.dataTime
            )
        }
        Spacer(Modifier.width(3.dp))
        MeasuringStationCard(
            modifier = Modifier.weight(1f),
            dropdownSelectedOption = dropdownSelectedOption,
            data = data
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMeasuringStationColumn(@PreviewParameter(AirQualityPreviewParamProvider::class) previewData: AirQualityViewState) {
    val stationFindState = previewData.stationFindUiState.model!!
    val rltmState = previewData.rltmStationUiState.model!!

    var dropdownSelectedOption by remember { mutableStateOf("통합 대기") }

    Column(
        modifier = Modifier.height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        StationColumnHeader(
            stationName = stationFindState.stationName
        )

        Spacer(modifier = Modifier.height(4.dp))

        StationColumnBody(
            dropdownSelectedOption = dropdownSelectedOption,
            data = rltmState,
            onOptionSelected = { dropdownSelectedOption = it }
        )
    }
}