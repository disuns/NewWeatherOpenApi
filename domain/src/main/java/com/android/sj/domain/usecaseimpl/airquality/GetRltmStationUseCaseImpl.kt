package com.android.sj.domain.usecaseimpl.airquality

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.usecase.airquality.GetRltmStationUseCase

class GetRltmStationUseCaseImpl (
    private val airQualityRepository: AirQualityRepository
) : GetRltmStationUseCase {
    override fun invoke(params: Map<String, String>) = airQualityRepository.fetchRltmStation(params)
}