package com.example.casestudy.ui.detail

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.casestudy.R
import com.example.casestudy.base.BaseFragment
import com.example.casestudy.databinding.FragmentDetailBinding
import com.example.casestudy.utils.ImageResizer
import com.example.casestudy.utils.gone
import com.example.casestudy.utils.show
import com.example.casestudy.utils.snackbar
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
            addFavorite()
        }

        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun addFavorite() {
        args.currentItem.let {
            if (!isInFavourite()) {
                viewModel.addFavorite(args.currentItem)

                binding.apply {
                    lottieAnimation.show()
                    detailLayout.gone()

                    lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {
                            Log.v("Animation", "Started")
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            lottieAnimation.gone()
                            detailLayout.show()
                        }

                        override fun onAnimationCancel(animation: Animator?) {
                            Log.v("Animation", "Canceled")
                        }

                        override fun onAnimationRepeat(animation: Animator?) {
                            Log.v("Animation", "Repeated")
                        }
                    })
                    likeButton.setImageResource(R.drawable.ic_like)
                }
            } else
                snackbar(binding.detailLayout, "This item is already in favourites")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.apply {
            args.currentItem.apply {
                Glide
                    .with(requireContext())
                    .load(artworkUrl100?.let { bigImage ->
                        ImageResizer.getBigImage(
                            bigImage
                        )
                    })
                    .into(detailImageView)

                detailArtistName.text = "Artist Name: " + args.currentItem.artistName
                detailKind.text = "Category: " + args.currentItem.kind
                releaseDateTextView.text = releaseDate?.substring(0, 10)

                detailCollectionName.text =
                    if (collectionName?.isNotEmpty() == true) collectionName else trackName

                detailPrice.text = when {
                    collectionPrice?.isNotEmpty() == true -> "$collectionPrice $currency"
                    price?.isNotEmpty() == true -> "$price $currency"
                    else -> "Price Not Found"
                }

                if (isInFavourite()) likeButton.setImageResource(R.drawable.ic_like)
                else likeButton.setImageResource(R.drawable.ic_dislike)
            }
        }
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