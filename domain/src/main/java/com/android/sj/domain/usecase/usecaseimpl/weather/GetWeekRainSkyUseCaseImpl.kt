package com.android.sj.domain.usecase.usecaseimpl.weather

import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeekRainSkyUseCase

class GetWeekRainSkyUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetWeekRainSkyUseCase {
    override fun invoke(landCode: String, time: String) = weatherRepository.fetchWeekRainSky(landCode, time)
}