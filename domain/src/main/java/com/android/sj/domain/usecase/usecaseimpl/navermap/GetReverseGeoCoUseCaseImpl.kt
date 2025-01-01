package com.android.sj.domain.usecase.usecaseimpl.navermap

import com.android.sj.domain.repositories.NaverMapRepository
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase

class GetReverseGeoCoUseCaseImpl (
    private val naverMapRepository: NaverMapRepository
) : GetReverseGeoCoUseCase {
    override fun invoke(latLng: String) = naverMapRepository.fetchReverseGeoCo(latLng)
}