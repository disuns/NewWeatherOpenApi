package com.codedevs.newweatheropenapi.di

import android.content.Context
import com.android.sj.presentation.utils.managers.TimeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideTimeManager(@ApplicationContext context: Context): TimeManager =
        TimeManager(context)
}