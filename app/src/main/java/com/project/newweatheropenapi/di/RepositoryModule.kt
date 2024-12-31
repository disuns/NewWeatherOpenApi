package com.project.newweatheropenapi.di

import com.android.sj.data.implementations.AirQualityRepositoryImpl
import com.android.sj.data.implementations.NaverMapRepositoryImpl
import com.android.sj.data.implementations.WeatherRepositoryImpl
import com.android.sj.data.remote.datasource.AirQualityDataSource
import com.android.sj.data.remote.datasource.NaverMapDataSource
import com.android.sj.data.remote.datasource.WeatherDataSource
import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.repositories.NaverMapRepository
import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.data.mapper.DataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAirQualityRepository(
        airQualityDataSource: AirQualityDataSource,
        mapper: DataMapper
    ): AirQualityRepository {
        return AirQualityRepositoryImpl(
            airQualityDataSource = airQualityDataSource,
            mapper = mapper
        )
    }
    @Provides
    @Singleton
    fun provideNaverMapRepository(
        naverMapDataSource: NaverMapDataSource,
        mapper: DataMapper
    ): NaverMapRepository {
        return NaverMapRepositoryImpl(
            naverMapDataSource = naverMapDataSource,
            mapper = mapper
        )
    }
    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherDataSource: WeatherDataSource,
        mapper: DataMapper
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherDataSource = weatherDataSource,
            mapper = mapper
        )
    }
}