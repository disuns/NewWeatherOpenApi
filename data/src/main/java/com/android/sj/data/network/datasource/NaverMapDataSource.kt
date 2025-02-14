package com.android.sj.data.network.datasource

import com.android.sj.data.network.response.navermap.NaverMapResponse
import com.android.sj.domain.ApiResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import retrofit2.http.QueryMap

interface NaverMapDataSource {
    fun fetchReverseGeoCo(@QueryMap params: Map<String, String>) : Channel<ApiResult<NaverMapResponse>>
}