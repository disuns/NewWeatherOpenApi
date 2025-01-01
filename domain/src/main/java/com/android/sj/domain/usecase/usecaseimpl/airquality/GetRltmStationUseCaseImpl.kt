package com.android.sj.domain.usecase.usecaseimpl.airquality

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase

class GetRltmStationUseCaseImpl (
    private val airQualityRepository: AirQualityRepository
) : GetRltmStationUseCase {
    override fun invoke(stationName: String) = airQualityRepository.fetchRltmStation(stationName)
}