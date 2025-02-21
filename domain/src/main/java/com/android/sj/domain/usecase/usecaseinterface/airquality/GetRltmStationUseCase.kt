package com.android.sj.domain.usecase.usecaseinterface.airquality

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.RltmStationData
import kotlinx.coroutines.channels.Channel

interface GetRltmStationUseCase {
    operator fun invoke(stationName: String) : Channel<ApiResult<RltmStationData>>
}