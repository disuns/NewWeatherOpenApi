package com.project.newweatheropenapi.ui.previewParamProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.project.newweatheropenapi.dataclass.TimeWeatherData

class CardDataPreviewParamProvider : PreviewParameterProvider<TimeWeatherData> {
    override val values = sequenceOf(
        TimeWeatherData(
            weatherDate = "20240916",
            weatherTime = "1800",
            temp = "29",
            sky = "3",
            rainPer = "30",
            rainMm = "강수없음",
            wet = "70",
            windDir = "156",
            windPower = "1.3",
            rain = "0"
        )
    )
}