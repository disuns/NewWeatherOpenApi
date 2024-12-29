package com.android.sj.domain.usecaseimpl.weather

import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.weather.GetWeekRainSkyUseCase

class GetWeekRainSkyUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetWeekRainSkyUseCase {
    override fun invoke(params: Map<String, String>) = weatherRepository.fetchWeekRainSky(params)
}