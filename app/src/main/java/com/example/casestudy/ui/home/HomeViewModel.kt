package com.example.casestudy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.casestudy.data.ApiRepository
import com.example.casestudy.data.entity.BaseResponse
import com.example.casestudy.data.entity.PopularSearch
import com.example.casestudy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var apiRepository: ApiRepository
) : ViewModel() {
    var limit = 20
    var listSize = 0

    var currentSearchText = ""
    var currentMedia = "movie"

    fun getNewsByQuery(term: String, media: String, limit: Int): LiveData<Resource<BaseResponse>> {
        return apiRepository.getSearchByQuery(term, media, limit)
    }

    fun getPopularSearchs(): ArrayList<PopularSearch>{
        return arrayListOf(
            PopularSearch("Fast and Furious"),
            PopularSearch("Billie Eilish"),
            PopularSearch("Harry Potter: The Complete Series"),
            PopularSearch("Bruno Mars"),
            PopularSearch("Robert Downey Jr."),
        )
    }
}