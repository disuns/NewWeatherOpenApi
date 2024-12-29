package com.android.sj.domain.usecaseimpl.weather

import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.weather.GetTimeWeatherUseCase

class GetTimeWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetTimeWeatherUseCase {
    override fun invoke(params: Map<String, String>) = weatherRepository.fetchTimeWeather(params)
}