package com.android.sj.presentation

import com.android.sj.presentation.mappers.AirQualityPresentationMapper
import com.android.sj.presentation.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.mappers.WeatherPresentationMapper
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.CoroutineScope

@AssistedFactory
interface NaverMapPresentationMapperFactory {
    fun create(scope : CoroutineScope): NaverMapPresentationMapper
}

@AssistedFactory
interface AirQualityPresentationMapperFactory{
    fun create(scope : CoroutineScope): AirQualityPresentationMapper
}

@AssistedFactory
interface WeatherPresentationMapperFactory{
    fun create(scope : CoroutineScope): WeatherPresentationMapper
}