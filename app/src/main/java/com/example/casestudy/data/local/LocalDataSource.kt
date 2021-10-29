package com.example.casestudy.data.local

import com.example.casestudy.data.entity.BaseResult
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val searchDao: SearchDao
) {
    fun getFavorites(): List<BaseResult> {
        return searchDao.getFavoriteNews()
    }

    fun addFavorite(search: BaseResult) {
        searchDao.addFavorite(search)
    }

    fun unFavorite(search: BaseResult) {
        searchDao.unFavorite(search)
    }
}