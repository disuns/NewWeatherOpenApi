package com.android.sj.presentation

import com.android.sj.presentation.mappers.AirQualityPresentationMapper
import com.android.sj.presentation.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.mappers.WeatherPresentationMapper
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.CoroutineScope

@AssistedFactory
interface MapperFactory {
    fun airQualityPresentation(scope : CoroutineScope): AirQualityPresentationMapper
    fun naverMapPresentationMapper(scope : CoroutineScope): NaverMapPresentationMapper
    fun weatherPresentationMapper(scope : CoroutineScope): WeatherPresentationMapper
}