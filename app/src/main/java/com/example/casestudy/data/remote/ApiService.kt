package com.example.casestudy.data.remote

import com.example.casestudy.data.entity.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("search?term=jack+johnson&media=ebook&limit=20")
    suspend fun getSearchByQuery(): Response<BaseResponse>
}