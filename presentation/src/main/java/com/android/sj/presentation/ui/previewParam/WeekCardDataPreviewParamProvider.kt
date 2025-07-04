package com.android.sj.presentation.ui.previewParam

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.sj.presentation.models.uimodels.weather.WeekRainSkyUIModel

class WeekCardDataPreviewParamProvider : PreviewParameterProvider<WeekRainSkyUIModel.Item> {
    override val values = sequenceOf(
        WeekRainSkyUIModel.Item(
            weekDate = "1234566",
            rainAm = "80",
            rainPm = "20",
            skyAm = "구름많고 비",
            skyPm = "맑음"
        )
    )
}