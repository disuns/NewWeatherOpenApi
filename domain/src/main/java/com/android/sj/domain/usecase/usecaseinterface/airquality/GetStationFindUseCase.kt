package com.android.sj.domain.usecase.usecaseinterface.airquality

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.StationFindData
import kotlinx.coroutines.channels.Channel

interface GetStationFindUseCase {
    operator fun invoke(regionX: String, regionY: String) : Channel<ApiResult<StationFindData>>
}