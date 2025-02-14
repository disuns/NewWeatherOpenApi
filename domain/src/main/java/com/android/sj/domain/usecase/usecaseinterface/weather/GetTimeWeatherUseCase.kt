package com.android.sj.domain.usecase.usecaseinterface.weather

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.TimeWeatherData
import kotlinx.coroutines.channels.Channel

interface GetTimeWeatherUseCase {
    operator fun invoke(date: String, time: String, lat: String, lon: String) : Channel<ApiResult<TimeWeatherData>>
}