package com.project.newweatheropenapi.di.usecasemodule

import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase
import com.android.sj.domain.usecase.usecaseimpl.airquality.GetAirQualityUseCaseImpl
import com.android.sj.domain.usecase.usecaseimpl.airquality.GetRltmStationUseCaseImpl
import com.android.sj.domain.usecase.usecaseimpl.airquality.GetStationFindUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AirQualityUseCaseModule {

    @Provides
    fun provideGetAirQualityUseCase(
        repository: AirQualityRepository
    ): GetAirQualityUseCase {
        return GetAirQualityUseCaseImpl(repository)
    }

    @Provides
    fun provideGetStationFindUseCas(
        repository: AirQualityRepository
    ): GetStationFindUseCase {
        return GetStationFindUseCaseImpl(repository)
    }

    @Provides
    fun provideGetRltmStationUseCase(
        repository: AirQualityRepository
    ): GetRltmStationUseCase {
        return GetRltmStationUseCaseImpl(repository)
    }
}