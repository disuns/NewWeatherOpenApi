package com.project.newweatheropenapi.ui.previewParamAndService

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.project.newweatheropenapi.dataclass.state.AirQualityViewState
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.RltmStationResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.StationFindResponse
import com.project.newweatheropenapi.sealed.intent.AirQualityIntent

class AirQualityPreviewParamProvider : PreviewParameterProvider<AirQualityViewState> {
    override val values = sequenceOf(
        AirQualityViewState(
            airQualityState = ApiResult.Success(
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
            ),
            stationFindState = ApiResult.Success(
                StationFindResponse(
                    response = StationFindResponse.Response(
                        body = StationFindResponse.Response.Body(
                            items = mutableListOf(
                                StationFindResponse.Response.Body.Item(
                                    tm = 1.0,
                                    addr = "서울특별시 영등포구 당산로 123 영등포구청 (당산동3가)",
                                    stationName = "영등포구"
                                ),
                                StationFindResponse.Response.Body.Item(
                                    tm = 1.6,
                                    addr = "서울 영등포구 영중로 37 (영등포시장사거리)",
                                    stationName = "영등포로"
                                ),
                                StationFindResponse.Response.Body.Item(
                                    tm = 2.4,
                                    addr = "서울 구로구 가마산로 27길 45 구로고등학교",
                                    stationName = "구로구"
                                )
                            ),
                            numOfRows = 10,
                            pageNo = 1,
                            totalCount = 3
                        ),
                        header = StationFindResponse.Response.Header(
                            resultCode = "00",
                            resultMsg = "NORMAL_CODE"
                        )
                    )
                )
            ),
            rltmStationState = ApiResult.Success(
                RltmStationResponse(
                    response = RltmStationResponse.Response(
                        body = RltmStationResponse.Response.Body(
                            items = mutableListOf(
                                RltmStationResponse.Response.Body.Item(
                                    coFlag = "null",
                                    coGrade = "1",
                                    coValue = "0.3",
                                    dataTime = "2024-09-19 14:00",
                                    khaiGrade = "2",
                                    khaiValue = "89",
                                    no2Flag = "null",
                                    no2Grade = "1",
                                    no2Value = "0.014",
                                    o3Flag = "null",
                                    o3Grade = "2",
                                    o3Value = "0.076",
                                    pm10Flag = "null",
                                    pm10Grade = "1",
                                    pm10Value = "22",
                                    pm10Value24 = "18",
                                    pm25Flag = "null",
                                    pm25Grade = "1",
                                    pm25Value = "16",
                                    pm25Value24 = "14",
                                    so2Flag = "null",
                                    so2Grade = "1",
                                    so2Value = "0.002"
                                ),
                                RltmStationResponse.Response.Body.Item(
                                    coFlag = "null",
                                    coGrade = "1",
                                    coValue = "0.3",
                                    dataTime = "2024-09-19 13:00",
                                    khaiGrade = "2",
                                    khaiValue = "78",
                                    no2Flag = "null",
                                    no2Grade = "1",
                                    no2Value = "0.013",
                                    o3Flag = "null",
                                    o3Grade = "2",
                                    o3Value = "0.063",
                                    pm10Flag = "null",
                                    pm10Grade = "1",
                                    pm10Value = "25",
                                    pm10Value24 = "17",
                                    pm25Flag = "null",
                                    pm25Grade = "1",
                                    pm25Value = "23",
                                    pm25Value24 = "13",
                                    so2Flag = "null",
                                    so2Grade = "1",
                                    so2Value = "0.002"
                                ),
                                RltmStationResponse.Response.Body.Item(
                                    coFlag = "null",
                                    coGrade = "1",
                                    coValue = "0.4",
                                    dataTime = "2024-09-19 12:00",
                                    khaiGrade = "2",
                                    khaiValue = "65",
                                    no2Flag = "null",
                                    no2Grade = "1",
                                    no2Value = "0.023",
                                    o3Flag = "null",
                                    o3Grade = "2",
                                    o3Value = "0.048",
                                    pm10Flag = "null",
                                    pm10Grade = "1",
                                    pm10Value = "21",
                                    pm10Value24 = "15",
                                    pm25Flag = "null",
                                    pm25Grade = "1",
                                    pm25Value = "19",
                                    pm25Value24 = "11",
                                    so2Flag = "null",
                                    so2Grade = "1",
                                    so2Value = "0.003"
                                )
                            ),
                            numOfRows = 100,
                            pageNo = 1,
                            totalCount = 23
                        ),
                        header = RltmStationResponse.Response.Header(
                            resultCode = "00",
                            resultMsg = "NORMAL_CODE"
                        )
                    )
                )
            )
        )
    )
}