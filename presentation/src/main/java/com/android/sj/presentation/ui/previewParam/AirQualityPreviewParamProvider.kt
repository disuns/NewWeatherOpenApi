package com.android.sj.presentation.ui.previewParam

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.common.state.uistate.AirQualityUiState
import com.android.sj.presentation.common.state.uistate.RltmStationUiState
import com.android.sj.presentation.common.state.uistate.StationFindUiState
import com.android.sj.presentation.common.state.viewstate.AirQualityViewState
import com.android.sj.presentation.models.uimodels.airquality.AirQualityUiModel
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel
import com.android.sj.presentation.models.uimodels.airquality.RltmStationUIModel.MeasuringData
import com.android.sj.presentation.models.uimodels.airquality.StationFindUIModel

class AirQualityPreviewParamProvider : PreviewParameterProvider<AirQualityViewState> {
    override val values = sequenceOf(
        AirQualityViewState(
            airQualityUiState = AirQualityUiState(
                model = AirQualityUiModel(
                    dataTimeAndCode = "PM10( code : 2024-09-21 11시 발표 )",
                    overall = "○ [미세먼지] 전 권역이 '좋음'으로 예상됩니다.",
                    cause = "○ [미세먼지] 원활한 대기 확산과 강수의 영향으로 대기질이 청정할 것으로 예상됩니다.",
                    actionKnack = null,
                    informGrades = "서울 : 좋음, 제주 : 좋음, 전남 : 좋음, 전북 : 좋음, 광주 : 좋음, 경남 : 좋음, 경북 : 좋음, 울산 : 좋음, 대구 : 좋음, 부산 : 좋음, 충남 : 좋음, 충북 : 좋음, 세종 : 좋음, 대전 : 좋음, 영동 : 좋음, 영서 : 좋음, 경기남부 : 좋음, 경기북부 : 좋음, 인천 : 좋음".split(",").toMutableList(),
                    imageUrl1 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/21/11/09km/AQF.20240920.NIER_09_01.PM10.1hsp.2024092103.png",
                    imageUrl2 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/21/11/09km/AQF.20240920.NIER_09_01.PM10.1hsp.2024092109.png",
                    imageUrl3 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/21/11/09km/AQF.20240920.NIER_09_01.PM10.1hsp.2024092115.png"
                )

            ),
            stationFindUiState = StationFindUiState(
                model = StationFindUIModel(stationName = "영등포구")
            ),
            rltmStationUiState = RltmStationUiState(
                model = RltmStationUIModel(
                    dataTime = "측정시간 : 2024-09-19 14:00",
                    measuringData = mutableListOf(
                        MeasuringData("89", "2", null),
                        MeasuringData("16", "1", "null"),
                        MeasuringData("22", "1", "null"),
                        MeasuringData("0.076", "2", "null"),
                        MeasuringData("0.3", "1", "null"),
                        MeasuringData("0.014", "1", "null"),
                        MeasuringData("0.002", "1", "null")
                    )
                )
            )
        )
    )
}