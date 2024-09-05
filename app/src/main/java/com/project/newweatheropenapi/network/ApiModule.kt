package com.project.newweatheropenapi.network

import com.project.newweatheropenapi.utils.DATA_POTAL_URL
import com.project.newweatheropenapi.utils.MAPS_URL
import com.project.newweatheropenapi.network.service.AirQualityService
import com.project.newweatheropenapi.network.service.NaverMapService
import com.project.newweatheropenapi.network.service.WeatherService
import com.project.newweatheropenapi.utils.AirQualityServiceRetrofit
import com.project.newweatheropenapi.utils.NaverMapServiceRetrofit
import com.project.newweatheropenapi.utils.WeatherServiceRetrofit
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
        val connectionTimeOut = (1000 * 30).toLong()
        val readTimeOut = (1000 * 5).toLong()

        return OkHttpClient.Builder()
            .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
            .connectTimeout(connectionTimeOut, TimeUnit.MILLISECONDS)
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
    fun provideAirQualityService(@NaverMapServiceRetrofit retrofit: Retrofit): AirQualityService =
        retrofit.create(AirQualityService::class.java)
}
