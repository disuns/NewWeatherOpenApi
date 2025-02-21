package com.codedevs.newweatheropenapi.di

import com.android.sj.common.utils.IoScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object ScopeModule {
    @IoScope
    @Provides
    fun provideIoScope() : CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
}