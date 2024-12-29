package com.android.sj.data.implementations

import com.android.sj.data.remote.datasource.NaverMapDataSource
import com.android.sj.domain.repositories.NaverMapRepository
import com.test.data.mapper.DataMapper
import javax.inject.Inject

class NaverMapRepositoryImpl @Inject constructor(
    private val naverMapDataSource: NaverMapDataSource,
    private val mapper : DataMapper
) : NaverMapRepository {
    override fun fetchReverseGeoCo(params: Map<String, String>) =
        mapper.responseToDomainReverseGeoCo(naverMapDataSource.fetchReverseGeoCo(params))
}