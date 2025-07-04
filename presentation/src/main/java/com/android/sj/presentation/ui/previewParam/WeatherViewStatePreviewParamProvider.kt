package com.android.sj.presentation.ui.previewParam

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.sj.presentation.enum.WeatherImgEnum
import com.android.sj.presentation.models.uimodels.weather.WeatherUIModel
import com.android.sj.presentation.state.uistate.WeatherUIState
import com.android.sj.presentation.state.viewstate.WeatherViewState

class WeatherViewStatePreviewParamProvider : PreviewParameterProvider<WeatherViewState> {
    override val values = sequenceOf(
        WeatherViewState(
            weatherUiState = WeatherUIState(
                model = WeatherUIModel(
                    nowTemp = "123",
                    nowRain = "123",
                    nowWet = "123",
                    nowWind = "123",
                    weatherImg = WeatherImgEnum.None,
                    weatherImgDrawable = null,
                    weatherText = "123",
                    windDir = "123"
                )
            )
        )
    )
}