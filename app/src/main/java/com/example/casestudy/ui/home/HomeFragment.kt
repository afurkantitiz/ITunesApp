package com.example.casestudy.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup.OnPositionChangedListener
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.data.entity.PopularSearch
import com.example.casestudy.databinding.FragmentHomeBinding
import com.example.casestudy.utils.Resource
import com.example.casestudy.utils.gone
import com.example.casestudy.utils.show
import com.example.casestudy.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    IClickListener, IPopularSearch {
    private val homeAdapter: HomeAdapter = HomeAdapter()
    private val popularSearchAdapter: PopularSearchAdapter = PopularSearchAdapter()
    private val viewModel: HomeViewModel by viewModels()
    private var homeList: ArrayList<BaseResult> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        searchViewListener()
        categoryListeners()
        onScrollListener()

        Log.v("popularList", viewModel.getPopularSearchs().size.toString())
    }

    private fun onScrollListener() {
        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.searchRecyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    if (viewModel.limit == viewModel.listSize) {
                        viewModel.limit += 20
                        getDataFromApi(viewModel.currentSearchText)
                    }
                }
            }
        })
    }

    private fun categoryListeners() {
        binding.buttonGroup.onPositionChangedListener =
            OnPositionChangedListener { currentPosition ->
                when (currentPosition) {
                    0 -> {
                        resetSearch()
                        viewModel.currentMedia = "movie"
                        getDataFromApi(viewModel.currentSearchText)
                    }
                    1 -> {
                        resetSearch()
                        viewModel.currentMedia = "music"
                        getDataFromApi(viewModel.currentSearchText)
                    }
                    2 -> {
                        resetSearch()
                        viewModel.currentMedia = "ebook"
                        getDataFromApi(viewModel.currentSearchText)
                    }
                    3 -> {
                        resetSearch()
                        viewModel.currentMedia = "podcast"
                        getDataFromApi(viewModel.currentSearchText)
                    }
                }
            }
    }

    private fun initViews() {
        homeAdapter.addListener(this)
        popularSearchAdapter.addListener(this)

        binding.apply {
            searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            searchRecyclerView.adapter = homeAdapter

            popularRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            popularRecyclerView.adapter = popularSearchAdapter
            popularSearchAdapter.setData(viewModel.getPopularSearchs() as List<PopularSearch>)
        }
    }

    private fun searchViewListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.currentSearchText = newText!!
                resetSearch()
                getDataFromApi(viewModel.currentSearchText)

                if (viewModel.currentSearchText.length <= 2) {
                    resetSearch()
                    viewModel.currentSearchText = ""

                    binding.searchRecyclerView.gone()
                    binding.popularLayout.show()
                }
                return true
            }
        })
    }

    private fun getDataFromApi(term: String) {
        if (term.length > 2) {
            viewModel.getNewsByQuery(term, viewModel.currentMedia, viewModel.limit)
                .observe(viewLifecycleOwner, { response ->
                    binding.apply {
                        when (response.status) {
                            Resource.Status.LOADING -> {
                                progressBar.show()
                            }
                            Resource.Status.SUCCESS -> {
                                progressBar.gone()
                                binding.searchRecyclerView.show()
                                binding.popularLayout.gone()

                                if (response.data?.resultCount!! > viewModel.listSize) {
                                    homeList.clear()

                                    viewModel.listSize = response.data.resultCount
                                    homeList.addAll(response.data.results!!)

                                    homeAdapter.setData(homeList)
                                    searchRecyclerView.adapter?.notifyItemInserted(homeList.size - 1)
                                }
                            }
                            Resource.Status.ERROR -> {
                                snackbar(constraintLayout, "ERROR")
                            }
                        }
                    }
                })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetSearch() {
        homeList.clear()
        viewModel.listSize = 0
        viewModel.limit = 20
        homeAdapter.notifyDataSetChanged()
    }

    override fun onClick(data: BaseResult?) {
        val action = data?.let { HomeFragmentDirections.actionHomeFragmentToDetailFragment(it) }
        if (action != null)
            findNavController().navigate(action)
    }

    override fun getPopularSearch(name: String?) {
        binding.apply {
            if (name != null) {
                searchView.setQuery(name, false)
                viewModel.currentSearchText = name
            }
            popularLayout.gone()
        }
    }
}