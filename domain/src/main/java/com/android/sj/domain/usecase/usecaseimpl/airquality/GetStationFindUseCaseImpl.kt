package com.android.sj.domain.usecase.usecaseimpl.airquality

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase

class GetStationFindUseCaseImpl (
    private val airQualityRepository: AirQualityRepository
) : GetStationFindUseCase {
    override fun invoke(regionX: String, regionY: String) = airQualityRepository.fetchStationFind(regionX, regionY)
}