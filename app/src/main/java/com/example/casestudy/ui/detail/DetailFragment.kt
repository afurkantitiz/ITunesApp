package com.example.casestudy.ui.detail

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.casestudy.R
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.databinding.FragmentDetailBinding
import com.example.casestudy.utils.gone
import com.example.casestudy.utils.show
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

                    binding.lottieAnimation.show()
                    binding.detailLayout.gone()

                    binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {
                            Log.v("Animation", "Started")
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            binding.lottieAnimation.gone()
                            binding.detailLayout.show()
                        }

                        override fun onAnimationCancel(animation: Animator?) {
                            Log.v("Animation", "Canceled")
                        }

                        override fun onAnimationRepeat(animation: Animator?) {
                            Log.v("Animation", "Repeated")
                        }
                    })
                    binding.likeButton.setImageResource(R.drawable.ic_like)
                } else
                    toast("This item is already in favourites")
            }
        }

        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun initViews() {
        args.currentItem.let {
            Glide
                .with(requireContext())
                .load(args.currentItem.artworkUrl100)
                .into(binding.detailImageView)

            binding.detailCurrency.text = args.currentItem.currency
            binding.detailArtistName.text = args.currentItem.artistName
            binding.detailKind.text = args.currentItem.kind
            binding.releaseDate.text = args.currentItem.releaseDate?.substring(0, 10)

            binding.detailCollectionName.text =
                if (args.currentItem.collectionName?.isNotEmpty() == true) args.currentItem.collectionName else args.currentItem.trackName

            binding.detailPrice.text = when {
                args.currentItem.collectionPrice?.isNotEmpty() == true -> args.currentItem.collectionPrice.toString() + " $"
                args.currentItem.price?.isNotEmpty() == true -> args.currentItem.price.toString() + " $"
                else -> "Price Not Found"
            }
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

    override fun isNavigationVisible(): Boolean = false
}