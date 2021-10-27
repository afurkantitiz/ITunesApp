package com.example.casestudy.data

import com.example.casestudy.data.remote.RemoteDataSource
import com.example.casestudy.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
) {
    fun getSearchByQuery() = performNetworkOperation {
        remoteDataSource.getSearchByQuery()
    }
}