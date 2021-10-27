package com.example.casestudy.data.remote

import com.example.casestudy.data.entity.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search?media=ebook&limit=20")
    suspend fun getSearchByQuery(
        @Query("term") term: String
    ): Response<BaseResponse>
}