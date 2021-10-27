package com.example.casestudy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.casestudy.data.ApiRepository
import com.example.casestudy.data.entity.BaseResponse
import com.example.casestudy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var apiRepository: ApiRepository
) : ViewModel() {

    fun getNewsByQuery(): LiveData<Resource<BaseResponse>> {
        return apiRepository.getSearchByQuery()
    }
}