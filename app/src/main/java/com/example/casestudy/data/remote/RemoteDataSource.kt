package com.example.casestudy.data.remote

import com.example.casestudy.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {
    suspend fun getSearchByQuery(term: String) = getResult {
        apiService.getSearchByQuery(term)
    }
}