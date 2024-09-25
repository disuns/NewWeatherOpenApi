package com.project.newweatheropenapi.ui.previewParamAndService

import com.project.newweatheropenapi.network.dataclass.response.datapotal.AirQualityResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.RltmStationResponse
import com.project.newweatheropenapi.network.dataclass.response.datapotal.StationFindResponse
import com.project.newweatheropenapi.network.service.AirQualityService
import retrofit2.Response


class FakeAirQualityService : AirQualityService {
    override suspend fun getAirQuality(params: Map<String, String>): Response<AirQualityResponse> {
        val fakeResponse = AirQualityResponse(
            response = AirQualityResponse.Response(
                body = AirQualityResponse.Response.Body(
                    totalCount = 12,
                    items = mutableListOf(
                        AirQualityResponse.Response.Body.Item(
                            actionKnack = "",
                            dataTime = "",
                            imageUrl7 = "",
                            imageUrl8 = "",
                            imageUrl9 = "",
                            imageUrl1 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/23/11/09km/AQF.20240922.NIER_09_01.PM10.1hsp.2024092303.png",
                            imageUrl2 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/23/11/09km/AQF.20240922.NIER_09_01.PM10.1hsp.2024092309.png",
                            imageUrl3 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/23/11/09km/AQF.20240922.NIER_09_01.PM10.1hsp.2024092315.png",
                            imageUrl4 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/23/11/09km/AQF.20240922.NIER_09_01.PM2P5.1hsp.2024092303.png",
                            imageUrl5 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/23/11/09km/AQF.20240922.NIER_09_01.PM2P5.1hsp.2024092309.png",
                            imageUrl6 = "https://www.airkorea.or.kr/file/proxyImage?fileName=2024/09/23/11/09km/AQF.20240922.NIER_09_01.PM2P5.1hsp.2024092315.png",
                            informCode = "PM10",
                            informCause = "○ [미세먼지] 원활한 대기 확산으로 대기질이 청정할 것으로 예상됩니다.",
                            informOverall = "○ [미세먼지] 전 권역이 '좋음'으로 예상됩니다.",
                            informData = "2024-09-23",
                            informGrade = "서울 : 좋음, 제주 : 좋음, 전남 : 좋음, 전북 : 좋음, 광주 : 좋음, 경남 : 좋음, 경북 : 좋음, 울산 : 좋음, 대구 : 좋음, 부산 : 좋음, 충남 : 좋음, 충북 : 좋음, 세종 : 좋음, 대전 : 좋음, 영동 : 좋음, 영서 : 좋음, 경기남부 : 좋음, 경기북부 : 좋음, 인천 : 좋음"
                        )
                    ),
                    pageNo = 1,
                    numOfRows = 100
                ),
                header = AirQualityResponse.Response.Header(
                    resultMsg = "NORMAL_CODE",
                    resultCode = "00"
                )
            )
        )
        return Response.success(fakeResponse)
    }

    override suspend fun getRltmStation(params: Map<String, String>): Response<RltmStationResponse> {
        val fakeResponse = RltmStationResponse(
            response = RltmStationResponse.Response(
                body = RltmStationResponse.Response.Body(
                    totalCount = 23,
                    items = mutableListOf(
                        RltmStationResponse.Response.Body.Item(
                            pm25Grade = "1",
                            pm10Value24 = "9",
                            so2Value = "-",
                            pm10Grade = "null",
                            o3Grade = "null",
                            pm10Value = "-",
                            pm25Flag = "통신장애",
                            khaiGrade = "null",
                            pm25Value = "-",
                            no2Flag = "통신장애",
                            no2Value = "-",
                            so2Grade = "null",
                            coFlag = "통신장애",
                            khaiValue = "-",
                            coValue = "-",
                            pm10Flag = "통신장애",
                            no2Grade = "null",
                            pm25Value24 = "5",
                            o3Flag = "통신장애",
                            so2Flag = "통신장애",
                            coGrade = "null",
                            dataTime = "2024-09-23 15:00",
                            o3Value = "-"
                        )
                    ),
                    pageNo = 1,
                    numOfRows = 100
                ),
                header = RltmStationResponse.Response.Header(
                    resultMsg = "NORMAL_CODE",
                    resultCode = "00"
                )
            )
        )
        return Response.success(fakeResponse)
    }

    override suspend fun getStationFind(params: Map<String, String>): Response<StationFindResponse> {
        val fakeResponse = StationFindResponse(
            response = StationFindResponse.Response(
                body = StationFindResponse.Response.Body(
                    totalCount = 3,
                    items = mutableListOf(
                        StationFindResponse.Response.Body.Item(
                            tm = 1.0,
                            addr = "서울특별시 영등포구 당산로 123 영등포구청 (당산동3가)",
                            stationName = "영등포구"
                        )
                    ),
                    pageNo = 1,
                    numOfRows = 10
                ),
                header = StationFindResponse.Response.Header(
                    resultMsg = "NORMAL_CODE",
                    resultCode = "00"
                )
            )
        )
        return Response.success(fakeResponse)
    }
}