package com.android.sj.domain.usecase.airquality

import com.android.sj.domain.models.RltmStationData
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetRltmStationUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<RltmStationData>>
}