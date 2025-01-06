package com.android.sj.domain.usecase.usecaseimpl.weather

import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.usecaseinterface.weather.GetTimeWeatherUseCase

class GetTimeWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetTimeWeatherUseCase {
    override fun invoke(date: String, time: String, lat: String, lon: String) = weatherRepository.fetchTimeWeather(date, time, lat, lon)
}