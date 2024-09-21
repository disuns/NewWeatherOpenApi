package com.project.newweatheropenapi.ui.previewParamProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.project.newweatheropenapi.dataclass.WeekDate
import com.project.newweatheropenapi.dataclass.WeekWeatherData

class WeekCardDataPreviewParamProvider : PreviewParameterProvider<WeekWeatherData> {
    override val values = sequenceOf(
        WeekWeatherData(
            weekDate = WeekDate(
                month = "09",
                day = "18",
                dayOfWeek = "수"
            ),
            rainAm = "80",
            rainPm = "20",
            skyAm = "구름많고 비",
            skyPm = "맑음"
        )
    )
}