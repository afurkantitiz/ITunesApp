package com.example.casestudy.data.entity

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("resultCount")
    val resultCount: Int?,
    @SerializedName("results")
    val results: List<Result>?
)