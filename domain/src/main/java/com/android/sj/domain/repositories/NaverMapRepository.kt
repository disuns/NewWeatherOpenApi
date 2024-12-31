package com.android.sj.domain.repositories

import com.android.sj.domain.models.NaverMapData
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow

interface NaverMapRepository {
    fun fetchReverseGeoCo(params: Map<String, String>) : Flow<ApiResult<NaverMapData>>
}