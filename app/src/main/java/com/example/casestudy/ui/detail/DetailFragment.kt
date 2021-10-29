package com.example.casestudy.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.currentItem.let {
            Glide
                .with(requireContext())
                .load(args.currentItem.artworkUrl100)
                .into(binding.detailImageView)
        }
    }

    override fun isNavigationVisible(): Boolean = false
}