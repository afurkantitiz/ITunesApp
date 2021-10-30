package com.example.casestudy.ui.favorite

import com.example.casestudy.data.entity.BaseResult

interface IUnFavoriteItem {
    fun unFavoriteItem(search: BaseResult, position: Int)
}