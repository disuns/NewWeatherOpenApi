package com.android.sj.domain.usecase.weather

import com.android.sj.domain.models.WeekRainSkyData
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetWeekRainSkyUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<WeekRainSkyData>>
}