package com.android.sj.domain.usecaseimpl.navermap

import com.android.sj.domain.repositories.NaverMapRepository
import com.android.sj.domain.usecase.navermap.GetReverseGeoCoUseCase

class GetReverseGeoCoUseCaseImpl (
    private val naverMapRepository: NaverMapRepository
) : GetReverseGeoCoUseCase {
    override fun invoke(params: Map<String, String>) = naverMapRepository.fetchReverseGeoCo(params)
}