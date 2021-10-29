package com.example.casestudy.di

import android.content.Context
import androidx.room.Room
import com.example.casestudy.data.local.LocalDataSource
import com.example.casestudy.data.local.RoomDB
import com.example.casestudy.data.local.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class
)
class DatabaseModule {
    @Provides
    fun localDataSource(
        searchDao: SearchDao
    ): LocalDataSource {
        return LocalDataSource(searchDao)
    }

    @Provides
    fun provideRoomDb(@ApplicationContext context: Context): RoomDB {
        return Room
            .databaseBuilder(context, RoomDB::class.java, "LocalDb")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(roomDB: RoomDB): SearchDao {
        return roomDB.searchDao()
    }
}