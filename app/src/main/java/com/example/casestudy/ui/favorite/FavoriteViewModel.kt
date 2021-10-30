package com.example.casestudy.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.casestudy.data.ApiRepository
import com.example.casestudy.data.entity.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private var apiRepository: ApiRepository
) : ViewModel() {
    fun getFavorites(): List<BaseResult> {
        return apiRepository.getFavorites()
    }
}