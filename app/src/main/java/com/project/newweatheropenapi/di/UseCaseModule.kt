package com.project.newweatheropenapi.di

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.repositories.NaverMapRepository
import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.domain.usecase.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.airquality.GetStationFindUseCase
import com.android.sj.domain.usecase.navermap.GetReverseGeoCoUseCase
import com.android.sj.domain.usecaseimpl.navermap.GetReverseGeoCoUseCaseImpl
import com.android.sj.domain.usecase.weather.GetTimeWeatherUseCase
import com.android.sj.domain.usecaseimpl.weather.GetTimeWeatherUseCaseImpl
import com.android.sj.domain.usecase.weather.GetWeatherUseCase
import com.android.sj.domain.usecase.weather.GetWeekRainSkyUseCase
import com.android.sj.domain.usecaseimpl.airquality.GetAirQualityUseCaseImpl
import com.android.sj.domain.usecaseimpl.airquality.GetRltmStationUseCaseImpl
import com.android.sj.domain.usecaseimpl.airquality.GetStationFindUseCaseImpl
import com.android.sj.domain.usecaseimpl.weather.GetWeatherUseCaseImpl
import com.android.sj.domain.usecaseimpl.weather.GetWeekRainSkyUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAirQualityUseCase(
        repository: AirQualityRepository
    ): GetAirQualityUseCase{
        return GetAirQualityUseCaseImpl(repository)
    }

    @Provides
    fun provideGetStationFindUseCas(
        repository: AirQualityRepository
    ): GetStationFindUseCase{
        return GetStationFindUseCaseImpl(repository)
    }

    @Provides
    fun provideGetRltmStationUseCase(
        repository: AirQualityRepository
    ): GetRltmStationUseCase{
        return GetRltmStationUseCaseImpl(repository)
    }

    @Provides
    fun provideGetReverseGeoCoUseCase(
        repository: NaverMapRepository
    ): GetReverseGeoCoUseCase{
        return GetReverseGeoCoUseCaseImpl(repository)
    }

    @Provides
    fun provideGetGetWeatherUseCase(
        repository: WeatherRepository
    ): GetWeatherUseCase{
        return GetWeatherUseCaseImpl(repository)
    }

    @Provides
    fun provideGetTimeWeatherUseCase(
        repository: WeatherRepository
    ): GetTimeWeatherUseCase{
        return GetTimeWeatherUseCaseImpl(repository)
    }

    @Provides
    fun provideGetGetWeekRainSkyUseCase(
        repository: WeatherRepository
    ): GetWeekRainSkyUseCase{
        return GetWeekRainSkyUseCaseImpl(repository)
    }
}