package com.example.casestudy.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup.OnPositionChangedListener
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.databinding.FragmentHomeBinding
import com.example.casestudy.utils.Resource
import com.example.casestudy.utils.gone
import com.example.casestudy.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeAdapter: HomeAdapter = HomeAdapter()
    private val viewModel: HomeViewModel by viewModels()
    private var homeList: ArrayList<BaseResult> = arrayListOf()

    private var currentSearchText = ""
    private var limitChanger = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        searchViewListener()
        categoryListeners()
    }

    private fun categoryListeners() {
        binding.buttonGroup.onPositionChangedListener =
            OnPositionChangedListener { currentPosition ->
                when (currentPosition) {
                    0 -> {
                        resetSearch()
                        getDataFromApi(currentSearchText, "movie")
                    }
                    1 -> {
                        resetSearch()
                        getDataFromApi(currentSearchText, "music")
                    }
                    2 -> {
                        resetSearch()
                        getDataFromApi(currentSearchText, "ebook")
                    }
                    3 -> {
                        resetSearch()
                        getDataFromApi(currentSearchText, "podcast")
                    }
                }
            }
    }

    private fun initViews() {
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchRecyclerView.adapter = homeAdapter
    }

    private fun searchViewListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchText = newText!!
                getDataFromApi(currentSearchText, "movie")

                if (currentSearchText.isEmpty()) {
                    resetSearch()
                    currentSearchText = ""
                }
                return true
            }
        })
    }

    private fun getDataFromApi(term: String, currentMedia: String) {
        if (term.length > 2) {
            viewModel.getNewsByQuery(term, currentMedia).observe(viewLifecycleOwner, { response ->
                when (response.status) {
                    Resource.Status.LOADING -> {
                        binding.progressBar.show()
                        binding.searchRecyclerView.gone()
                    }
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.gone()
                        binding.searchRecyclerView.show()

                        if (response.data?.results?.size != 0) {
                            homeList = (response.data?.results as ArrayList<BaseResult>?)!!
                            homeAdapter.setData(homeList)
                        } else
                            homeAdapter.setData(emptyList())
                    }
                    Resource.Status.ERROR -> {
                        binding.searchRecyclerView.gone()
                        binding.notFoundImageView.show()
                    }
                }
            })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetSearch() {
        limitChanger = 1
        homeList.clear()
        homeAdapter.notifyDataSetChanged()
    }
}