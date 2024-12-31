package com.android.sj.data.remote.datasource

import com.android.sj.data.remote.response.navermap.NaverMapResponse
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.QueryMap

interface NaverMapDataSource {
    fun fetchReverseGeoCo(@QueryMap params: Map<String, String>) : Flow<ApiResult<NaverMapResponse>>
}