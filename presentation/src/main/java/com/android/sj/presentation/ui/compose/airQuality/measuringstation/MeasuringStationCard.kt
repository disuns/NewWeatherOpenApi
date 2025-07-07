package com.android.sj.presentation.ui.compose.airQuality.measuringstation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.android.sj.presentation.R
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel.MeasuringData
import com.android.sj.presentation.ui.theme.Color_F0FFF0
import com.android.sj.presentation.utils.rltmFlag
import com.android.sj.presentation.utils.rltmGradeConvert
import com.android.sj.presentation.utils.rltmValueConvert

@Composable
fun MeasuringStationCard(
    modifier: Modifier,
    dropdownSelectedOption: String,
    data: RltmStationUIModel
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