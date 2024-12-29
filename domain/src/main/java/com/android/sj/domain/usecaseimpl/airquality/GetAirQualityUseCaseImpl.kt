package com.android.sj.domain.usecaseimpl.airquality

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.usecase.airquality.GetAirQualityUseCase

class GetAirQualityUseCaseImpl(
    private val airQualityRepository: AirQualityRepository
) : GetAirQualityUseCase {
    override fun invoke(params: Map<String, String>) = airQualityRepository.fetchAirQuality(params)
}