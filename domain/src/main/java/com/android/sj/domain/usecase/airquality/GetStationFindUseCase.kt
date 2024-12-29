package com.android.sj.domain.usecase.airquality

import com.android.sj.domain.models.StationFindData
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetStationFindUseCase {
    operator fun invoke(params: Map<String, String>) : Flow<ApiResult<StationFindData>>
}