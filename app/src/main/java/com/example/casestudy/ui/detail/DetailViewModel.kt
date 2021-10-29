package com.example.casestudy.ui.detail

import androidx.lifecycle.ViewModel
import com.example.casestudy.data.ApiRepository
import com.example.casestudy.data.entity.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private var apiRepository: ApiRepository
) : ViewModel() {
    fun addFavorite(source: BaseResult) {
        apiRepository.addFavorite(source)
    }

    fun getFavorites(): List<BaseResult> {
        return apiRepository.getFavorites()
    }
}