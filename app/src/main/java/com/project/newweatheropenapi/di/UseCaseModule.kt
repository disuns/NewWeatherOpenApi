package com.test.base.di

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
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindGetAirQualityUseCase(
        impl: GetAirQualityUseCaseImpl
    ): GetAirQualityUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetStationFindUseCase(
        impl: GetStationFindUseCaseImpl
    ): GetStationFindUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetRltmStationUseCase(
        impl: GetRltmStationUseCaseImpl
    ): GetRltmStationUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetReverseGeoCoUseCase(
        impl: GetReverseGeoCoUseCaseImpl
    ): GetReverseGeoCoUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetWeatherUseCase(
        impl: GetWeatherUseCaseImpl
    ): GetWeatherUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetTimeWeatherUseCase(
        impl: GetTimeWeatherUseCaseImpl
    ): GetTimeWeatherUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetWeekRainSkyUseCase(
        impl: GetWeekRainSkyUseCaseImpl
    ): GetWeekRainSkyUseCase
}