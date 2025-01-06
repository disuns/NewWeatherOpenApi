package com.android.sj.domain.usecase.usecaseimpl.weather

import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeatherUseCase

class GetWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetWeatherUseCase {
    override fun invoke(date: String, time: String, lat: String, lon: String) = weatherRepository.fetchWeather(date, time, lat, lon)
}