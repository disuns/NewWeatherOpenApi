package com.codedevs.newweatheropenapi.di

import com.android.sj.common.utils.DATA_POTAL_URL
import com.android.sj.common.utils.MAPS_URL
import com.android.sj.data.network.service.AirQualityService
import com.android.sj.data.network.service.NaverMapService
import com.android.sj.data.network.service.WeatherService
import com.android.sj.common.utils.AirQualityServiceRetrofit
import com.android.sj.common.utils.NaverMapServiceRetrofit
import com.android.sj.common.utils.WeatherServiceRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(provideLoggingInterceptor())
            .build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor ()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideDataPotalRetrofit(okHttpClient: OkHttpClient) =
        createRetrofit(okHttpClient, DATA_POTAL_URL)

    @Singleton
    @WeatherServiceRetrofit
    @Provides
    fun provideWeatherServiceRetrofit(okHttpClient: OkHttpClient): Retrofit =
        provideDataPotalRetrofit(okHttpClient)

    @Singleton
    @AirQualityServiceRetrofit
    @Provides
    fun provideAirQualityServiceRetrofit(okHttpClient: OkHttpClient): Retrofit =
        provideDataPotalRetrofit(okHttpClient)

    @Singleton
    @NaverMapServiceRetrofit
    @Provides
    fun provideNaverMapServiceRetrofit(okHttpClient: OkHttpClient) =
        createRetrofit(okHttpClient, MAPS_URL)

    @Singleton
    @Provides
    fun provideWeatherService(@WeatherServiceRetrofit retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideNaverMapService(@NaverMapServiceRetrofit retrofit: Retrofit): NaverMapService =
        retrofit.create(NaverMapService::class.java)

    @Singleton
    @Provides
    fun provideAirQualityService(@WeatherServiceRetrofit retrofit: Retrofit): AirQualityService =
        retrofit.create(AirQualityService::class.java)
}
