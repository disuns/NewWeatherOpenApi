package com.android.sj.domain.usecaseimpl.weather

import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.weather.GetWeatherUseCase

class GetWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetWeatherUseCase {
    override fun invoke(params: Map<String, String>) = weatherRepository.fetchWeather(params)
}