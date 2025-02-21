package com.android.sj.common.utils

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherServiceRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverMapServiceRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AirQualityServiceRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoScope