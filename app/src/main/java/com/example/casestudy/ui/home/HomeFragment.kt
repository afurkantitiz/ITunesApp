package com.example.casestudy.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.databinding.FragmentHomeBinding
import com.example.casestudy.utils.Resource
import com.example.casestudy.utils.gone
import com.example.casestudy.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeAdapter: HomeAdapter = HomeAdapter()
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getDataForApi()
    }

    private fun initViews() {
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchRecyclerView.adapter = homeAdapter
    }

    private fun getDataForApi() {
        viewModel.getNewsByQuery().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                    binding.searchRecyclerView.gone()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.gone()
                    binding.searchRecyclerView.show()

                    if (response.data?.results?.size != 0)
                        homeAdapter.setData(response.data?.results!!)
                }
                Resource.Status.ERROR -> {
                    Log.v("Search", "Error")
                }
            }
        })
    }
}