package com.project.newweatheropenapi.ui.previewParamProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.RltmStationResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.StationFindResponse
import com.project.newweatheropenapi.ui.compose.airQuality.StationPreviewData

class StationPreviewDataProvider : PreviewParameterProvider<StationPreviewData> {
    override val values = sequenceOf(
        StationPreviewData(
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
                                // 필요에 따라 더 많은 항목 추가
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