package com.android.sj.domain.usecase.usecaseinterface.weather

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.WeekRainSkyData
import kotlinx.coroutines.flow.Flow

interface GetWeekRainSkyUseCase {
    operator fun invoke(landCode: String, time: String) : Flow<ApiResult<WeekRainSkyData>>
}