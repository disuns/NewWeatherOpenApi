package com.android.sj.domain.usecase.usecaseinterface.navermap

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.NaverMapData
import kotlinx.coroutines.channels.Channel

interface GetReverseGeoCoUseCase {
    operator fun invoke(latLng: String) : Channel<ApiResult<NaverMapData>>
}