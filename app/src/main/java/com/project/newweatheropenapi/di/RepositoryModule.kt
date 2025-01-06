package com.project.newweatheropenapi.di

import com.android.sj.data.impl.repositorys.AirQualityRepositoryImpl
import com.android.sj.data.impl.repositorys.NaverMapRepositoryImpl
import com.android.sj.data.impl.repositorys.WeatherRepositoryImpl
import com.android.sj.data.mapper.AirQualityDataMapper
import com.android.sj.data.mapper.NaverMapDataMapper
import com.android.sj.data.network.datasource.AirQualityDataSource
import com.android.sj.data.network.datasource.NaverMapDataSource
import com.android.sj.data.network.datasource.WeatherDataSource
import com.android.sj.domain.repositories.AirQualityRepository
import com.android.sj.domain.repositories.NaverMapRepository
import com.android.sj.domain.repositories.WeatherRepository
import com.android.sj.data.mapper.WeatherDataMapper
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
        mapper: AirQualityDataMapper
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
        mapper: NaverMapDataMapper
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
        mapper: WeatherDataMapper
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherDataSource = weatherDataSource,
            mapper = mapper
        )
    }
}