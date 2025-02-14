package com.android.sj.domain.usecase.usecaseinterface.airquality

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.AirQualityData
import kotlinx.coroutines.channels.Channel

interface GetAirQualityUseCase {
    operator fun invoke(airQualityDate: String) : Channel<ApiResult<AirQualityData>>
}