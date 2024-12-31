package com.project.newweatheropenapi.di

import android.content.Context
import com.android.sj.common.utils.managers.LocationDataManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationManager(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationDataManager(fusedLocationProviderClient: FusedLocationProviderClient): LocationDataManager {
        return LocationDataManager(fusedLocationProviderClient)
    }
}