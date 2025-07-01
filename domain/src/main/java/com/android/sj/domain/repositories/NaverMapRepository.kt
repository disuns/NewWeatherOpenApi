package com.android.sj.domain.repositories

import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.NaverMapData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface NaverMapRepository {
    fun fetchReverseGeoCo(latLng: String) : Flow<ApiResult<NaverMapData>>
}