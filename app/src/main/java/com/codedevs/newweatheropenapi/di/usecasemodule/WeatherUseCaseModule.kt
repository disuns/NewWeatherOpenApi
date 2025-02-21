package com.codedevs.newweatheropenapi.di.usecasemodule

import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.usecaseinterface.weather.GetTimeWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeekRainSkyUseCase
import com.android.sj.domain.usecase.usecaseimpl.weather.GetTimeWeatherUseCaseImpl
import com.android.sj.domain.usecase.usecaseimpl.weather.GetWeatherUseCaseImpl
import com.android.sj.domain.usecase.usecaseimpl.weather.GetWeekRainSkyUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeatherUseCaseModule {

    @Provides
    fun provideGetGetWeatherUseCase(
        repository: WeatherRepository
    ): GetWeatherUseCase {
        return GetWeatherUseCaseImpl(repository)
    }

    @Provides
    fun provideGetTimeWeatherUseCase(
        repository: WeatherRepository
    ): GetTimeWeatherUseCase {
        return GetTimeWeatherUseCaseImpl(repository)
    }

    @Provides
    fun provideGetGetWeekRainSkyUseCase(
        repository: WeatherRepository
    ): GetWeekRainSkyUseCase {
        return GetWeekRainSkyUseCaseImpl(repository)
    }
}