package com.example.casestudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.casestudy.data.entity.BaseResult

@Database(entities = [BaseResult::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}