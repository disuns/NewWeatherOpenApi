package com.android.sj.domain.repositories

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.NaverMapData
import kotlinx.coroutines.channels.Channel

interface NaverMapRepository {
    fun fetchReverseGeoCo(latLng: String) : Channel<ApiResult<NaverMapData>>
}