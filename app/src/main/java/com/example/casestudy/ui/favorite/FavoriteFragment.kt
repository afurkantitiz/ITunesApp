package com.example.casestudy.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.databinding.FragmentFavoriteBinding
import com.example.casestudy.utils.SwipeToCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    IUnFavoriteItem {
    private val viewModel: FavoriteViewModel by viewModels()
    private val favoriteAdapter: FavoriteAdapter = FavoriteAdapter()
    private lateinit var favoriteList: ArrayList<BaseResult>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        favoriteList = viewModel.getFavorites() as ArrayList<BaseResult>
        favoriteAdapter.addListener(this)

        binding.apply {
            favoriteRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            favoriteAdapter.setFavorite(favoriteList)
            favoriteRecyclerView.adapter = favoriteAdapter
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToCard(favoriteAdapter, requireContext()))
        itemTouchHelper.attachToRecyclerView(binding.favoriteRecyclerView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun unFavoriteItem(search: BaseResult, position: Int) {
        viewModel.unFavoriteNews(search)
        favoriteList.removeAt(position)
        favoriteAdapter.setFavorite(favoriteList)
        favoriteAdapter.notifyItemRemoved(position)
        favoriteAdapter.notifyDataSetChanged()
    }
}