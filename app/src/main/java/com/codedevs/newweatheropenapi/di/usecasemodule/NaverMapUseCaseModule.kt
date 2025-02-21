package com.codedevs.newweatheropenapi.di.usecasemodule

import com.android.sj.domain.repositories.NaverMapRepository
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.domain.usecase.usecaseimpl.navermap.GetReverseGeoCoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NaverMapUseCaseModule {

    @Provides
    fun provideGetReverseGeoCoUseCase(
        repository: NaverMapRepository
    ): GetReverseGeoCoUseCase {
        return GetReverseGeoCoUseCaseImpl(repository)
    }
}