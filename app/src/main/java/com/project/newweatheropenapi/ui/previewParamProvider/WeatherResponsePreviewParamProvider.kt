package com.project.newweatheropenapi.ui.previewParamProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse

class WeatherResponsePreviewParamProvider : PreviewParameterProvider<ApiResult<WeatherResponse>> {
    override val values = sequenceOf(
        ApiResult.Success(
            WeatherResponse(
                response = WeatherResponse.Response(
                    header = WeatherResponse.Response.Header(
                        resultCode = "00",
                        resultMsg = "NORMAL_SERVICE"
                    ),
                    body = WeatherResponse.Response.Body(
                        dataType = "JSON",
                        items = WeatherResponse.Response.Body.Items(
                            item = mutableListOf(
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "LGT",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "LGT",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1300",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "LGT",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1400",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "PTY",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "0"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "RN1",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "강수없음"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "SKY",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "1"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "T1H",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "28"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "REH",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "60"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "UUU",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "-2"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "VVV",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "0.1"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "VEC",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "94"
                                ),
                                WeatherResponse.Response.Body.Items.Item(
                                    baseDate = "20240916",
                                    baseTime = "1130",
                                    category = "WSD",
                                    nx = 58,
                                    ny = 126,
                                    fcstDate = "20240916",
                                    fcstTime = "1200",
                                    fcstValue = "2"
                                )
                            )
                        ),
                        numOfRows = 1000,
                        pageNo = 1,
                        totalCount = 60
                    )
                )
            )
        )
    )
}