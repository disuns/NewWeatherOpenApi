package com.project.newweatheropenapi.network

import android.util.Log
import com.getkeepsafe.relinker.BuildConfig
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.project.newweatheropenapi.DATA_POTAL_URL
import com.project.newweatheropenapi.MAPS_URL
import com.project.newweatheropenapi.network.service.NaverMapService
import com.project.newweatheropenapi.network.service.WeatherService
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

    private fun provideLoggingInterceptor():HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor{ message ->
            if (!message.startsWith("{") && !message.startsWith("[")) {
                Log.d("OkHttp",message)
                return@HttpLoggingInterceptor
            }
            try {
                Log.d("OkHttp",
                    GsonBuilder().setPrettyPrinting().create().toJson(
                        JsonParser().parse(message)))
            } catch (m: JsonSyntaxException) {
                Log.d("OkHttp",message)
            }
        }

        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY // 디버그 모드일 때는 BODY 레벨
        } else {
            HttpLoggingInterceptor.Level.NONE // 릴리즈 모드에서는 로깅 비활성화
        }

        return interceptor
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @WeatherServiceRetrofit
    @Provides
    fun provideWeatherServiceRetrofit(okHttpClient: OkHttpClient) = createRetrofit(okHttpClient, DATA_POTAL_URL)

    @Singleton
    @NaverMapServiceRetrofit
    @Provides
    fun provideNaverMapServiceRetrofit(okHttpClient: OkHttpClient) = createRetrofit(okHttpClient, MAPS_URL)

    @Singleton
    @Provides
    fun provideWeatherService(@WeatherServiceRetrofit retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideNaverMapService(@NaverMapServiceRetrofit retrofit: Retrofit): NaverMapService = retrofit.create(NaverMapService::class.java)
}