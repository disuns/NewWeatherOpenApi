package com.android.sj.domain.usecaseimpl.airquality

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.usecase.airquality.GetStationFindUseCase

class GetStationFindUseCaseImpl (
    private val airQualityRepository: AirQualityRepository
) : GetStationFindUseCase {
    override fun invoke(params: Map<String, String>) = airQualityRepository.fetchStationFind(params)
}