package com.project.newweatheropenapi.ui.previewParamAndService

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse

class AirQualityPreviewParamProvider : PreviewParameterProvider<ApiResult<AirQualityResponse>> {
    override val values = sequenceOf(
        ApiResult.Success(
            AirQualityResponse(
                response = AirQualityResponse.Response(
                    header = AirQualityResponse.Response.Header(
                        resultCode = "00",
                        resultMsg = "NORMAL_SERVICE"
                    ),
                    body = AirQualityResponse.Response.Body(
                        items = mutableListOf(
                            AirQualityResponse.Response.Body.Item(
                                actionKnack = null,
                                dataTime = "2024-09-21 11시 발표",
                                imageUrl1 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/21/11/09km/AQF.20240920.NIER_09_01.PM10.1hsp.2024092103.png",
                                imageUrl2 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/21/11/09km/AQF.20240920.NIER_09_01.PM10.1hsp.2024092109.png",
                                imageUrl3 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/21/11/09km/AQF.20240920.NIER_09_01.PM10.1hsp.2024092115.png",
                                imageUrl4 = "null",
                                imageUrl5 = "null",
                                imageUrl6 = "null",
                                imageUrl7 = "null",
                                imageUrl8 = "null",
                                imageUrl9 = "null",
                                informCause = "○ [미세먼지] 원활한 대기 확산과 강수의 영향으로 대기질이 청정할 것으로 예상됩니다.",
                                informCode = "PM10",
                                informData = "2024-09-21",
                                informGrade = "서울 : 좋음, 제주 : 좋음, 전남 : 좋음, 전북 : 좋음, 광주 : 좋음, 경남 : 좋음, 경북 : 좋음, 울산 : 좋음, 대구 : 좋음, 부산 : 좋음, 충남 : 좋음, 충북 : 좋음, 세종 : 좋음, 대전 : 좋음, 영동 : 좋음, 영서 : 좋음, 경기남부 : 좋음, 경기북부 : 좋음, 인천 : 좋음",
                                informOverall = "○ [미세먼지] 전 권역이 '좋음'으로 예상됩니다."
                            ),
                        ),
                        numOfRows = 100,
                        pageNo = 1,
                        totalCount = 12
                    )
                )
            )
        )
    )
}