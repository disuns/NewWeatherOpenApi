package com.project.newweatheropenapi.di

import com.android.sj.data.remote.datasource.AirQualityDataSource
import com.android.sj.data.remote.datasource.NaverMapDataSource
import com.android.sj.data.remote.datasource.WeatherDataSource
import com.android.sj.data.remote.datasource.impl.AirQualityDataSourceImpl
import com.android.sj.data.remote.datasource.impl.NaverMapDataSourceImpl
import com.android.sj.data.remote.datasource.impl.WeatherDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindAirQualityDataSource(
        airQualityDataSourceImpl : AirQualityDataSourceImpl
    ): AirQualityDataSource

    @Binds
    @Singleton
    abstract fun bindNaverMapDataSource(
        naverMapDataSourceImpl : NaverMapDataSourceImpl
    ): NaverMapDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherDataSourceImpl(
        weatherDataSourceImpl: WeatherDataSourceImpl
    ): WeatherDataSource
}