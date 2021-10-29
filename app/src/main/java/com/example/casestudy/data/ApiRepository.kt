package com.example.casestudy.data

import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.data.local.LocalDataSource
import com.example.casestudy.data.remote.RemoteDataSource
import com.example.casestudy.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource
) {
    fun getSearchByQuery(term: String, media: String, limit: Int) = performNetworkOperation {
        remoteDataSource.getSearchByQuery(term, media, limit)
    }

    fun getFavorites() = localDataSource.getFavorites()

    fun addFavorite(search: BaseResult) = localDataSource.addFavorite(search)

    fun unFavorite(search: BaseResult) = localDataSource.unFavorite(search)
}