package com.android.sj.domain.usecase.usecaseinterface.airquality

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.RltmStationData
import kotlinx.coroutines.flow.Flow

interface GetRltmStationUseCase {
    operator fun invoke(stationName: String) : Flow<ApiResult<RltmStationData>>
}