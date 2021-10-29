package com.example.casestudy.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.casestudy.R
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.databinding.FragmentDetailBinding
import com.example.casestudy.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        onClickListener()
    }

    private fun onClickListener() {
        binding.likeButton.setOnClickListener {
            args.currentItem.let {
                if (!isInFavourite()) {
                    viewModel.addFavorite(
                        BaseResult(
                            id = 0,
                            collectionName = args.currentItem.collectionName,
                            artworkUrl100 = args.currentItem.artworkUrl100,
                            artistName = args.currentItem.artistName,
                            currency = args.currentItem.currency,
                            kind = args.currentItem.kind,
                            releaseDate = args.currentItem.releaseDate,
                            trackId = args.currentItem.trackId,
                            trackName = args.currentItem.trackName,
                            collectionPrice = args.currentItem.collectionPrice,
                            price = args.currentItem.price
                        )
                    )
                    toast("Successfully added to favourites")
                    binding.likeButton.setImageResource(R.drawable.ic_like)
                } else
                    toast("This item is already in favourites")
            }
        }
    }

    private fun initViews() {
        args.currentItem.let {
            Glide
                .with(requireContext())
                .load(args.currentItem.artworkUrl100)
                .into(binding.detailImageView)
        }

        if (isInFavourite()) binding.likeButton.setImageResource(R.drawable.ic_like)
        else binding.likeButton.setImageResource(R.drawable.ic_dislike)
    }

    private fun isInFavourite(): Boolean {
        var isInFavourite = false

        for (data in viewModel.getFavorites()) {
            isInFavourite = data.trackId == args.currentItem.trackId
            if (isInFavourite) break
        }
        return isInFavourite
    }
}