package com.test.base.di

import android.content.Context
import com.test.data.local.dao.CoinDao
import com.test.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.room.Room

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database")
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDAO(noteDB: AppDatabase): CoinDao {
        return noteDB.coinDao()
    }
}