package com.android.sj.domain.usecase.usecaseinterface.airquality

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.StationFindData
import kotlinx.coroutines.flow.Flow

interface GetStationFindUseCase {
    operator fun invoke(regionX: String, regionY: String) : Flow<ApiResult<StationFindData>>
}