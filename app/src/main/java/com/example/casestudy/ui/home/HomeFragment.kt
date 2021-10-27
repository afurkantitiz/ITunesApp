package com.example.casestudy.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
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

    private var limitChanger = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getDataForApi()
        searchViewListener()
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
                if (newText?.length!! > 2){
                    getDataForApi(newText)
                }else if (newText.isEmpty()){
                    resetSearch()
                }
                return true
            }
        })
    }

    private fun getDataForApi(term: String = "") {
        viewModel.getNewsByQuery(term).observe(viewLifecycleOwner, { response ->
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
                    }
                }
                Resource.Status.ERROR -> {
                    Log.v("Search", "Error")
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetSearch() {
        limitChanger = 1
        homeList.clear()
        homeAdapter.notifyDataSetChanged()
    }
}