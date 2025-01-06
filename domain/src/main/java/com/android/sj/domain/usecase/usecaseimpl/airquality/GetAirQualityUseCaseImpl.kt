package com.android.sj.domain.usecase.usecaseimpl.airquality

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase

class GetAirQualityUseCaseImpl(
    private val airQualityRepository: AirQualityRepository
) : GetAirQualityUseCase {
    override fun invoke(airQualityDate: String) = airQualityRepository.fetchAirQuality(airQualityDate)
}