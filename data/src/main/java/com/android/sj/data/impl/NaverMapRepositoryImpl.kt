package com.android.sj.data.impl

import com.android.sj.data.mapper.NaverMapDataMapper
import com.android.sj.data.network.datasource.NaverMapDataSource
import com.android.sj.data.network.request.navermap.NaverMapRequest
import com.android.sj.data.network.request.navermap.toMap
import com.android.sj.domain.ApiResult
import com.android.sj.domain.models.NaverMapData
import com.android.sj.domain.repositories.NaverMapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NaverMapRepositoryImpl @Inject constructor(
    private val naverMapDataSource: NaverMapDataSource,
    private val mapper : NaverMapDataMapper
) : NaverMapRepository {
    override fun fetchReverseGeoCo(latLng: String): Flow<ApiResult<NaverMapData>> {
        val request = NaverMapRequest(coords = latLng)
        return mapper.responseToDomainReverseGeoCo(naverMapDataSource.fetchReverseGeoCo(request.toMap()))
    }

}