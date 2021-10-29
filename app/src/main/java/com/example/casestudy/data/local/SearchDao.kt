package com.example.casestudy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.casestudy.data.entity.BaseResult

@Dao
interface SearchDao {
    @Query("SELECT * FROM search ORDER BY id DESC")
    fun getFavoriteNews(): List<BaseResult>

    @Insert
    fun addFavorite(search: BaseResult?)

    @Delete
    fun unFavorite(search: BaseResult?)
}