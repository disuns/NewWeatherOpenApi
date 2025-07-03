package com.android.sj.presentation.common.ui.previewParam

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.common.enum.WeatherImgEnum
import com.android.sj.presentation.common.state.uistate.WeatherUIState
import com.android.sj.presentation.common.state.viewstate.WeatherViewState
import com.android.sj.presentation.common.models.uimodels.weather.WeatherUIModel

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