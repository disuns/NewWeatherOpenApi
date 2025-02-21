package com.codedevs.newweatheropenapi.di

import com.android.sj.data.network.datasource.AirQualityDataSource
import com.android.sj.data.network.datasource.NaverMapDataSource
import com.android.sj.data.network.datasource.WeatherDataSource
import com.android.sj.data.network.datasource.impl.AirQualityDataSourceImpl
import com.android.sj.data.network.datasource.impl.NaverMapDataSourceImpl
import com.android.sj.data.network.datasource.impl.WeatherDataSourceImpl
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