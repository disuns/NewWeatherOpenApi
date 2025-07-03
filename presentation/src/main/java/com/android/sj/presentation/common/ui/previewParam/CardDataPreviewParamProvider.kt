package com.android.sj.presentation.common.ui.previewParam

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.sj.presentation.common.models.uimodels.weather.TimeWeatherUIModel

class CardDataPreviewParamProvider : PreviewParameterProvider<TimeWeatherUIModel.Item> {
    override val values = sequenceOf(
        TimeWeatherUIModel.Item(
            weatherDate = "123",
            weatherTime = "123",
            temp = "123",
            rainPer = "123",
            rainMm = "123",
            wet = "123",
            windDir = "123",
            windPower = "123",
            imgDrawable = null
        )
    )
}