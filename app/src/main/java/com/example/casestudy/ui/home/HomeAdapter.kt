package com.example.casestudy.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.casestudy.R
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.databinding.ItemSearchCardBinding
import com.example.casestudy.utils.ImageResizer

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var searchList: List<BaseResult> = emptyList()
    private var listener: IClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding =
            ItemSearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val search: BaseResult = searchList[position]

        holder.binding.apply {
            nameTextView.text =
                if (search.collectionName?.isNotEmpty() == true) search.collectionName else search.trackName

            priceTextView.text = when {
                search.collectionPrice?.isNotEmpty() == true -> search.collectionPrice.toString() + " $"
                search.price?.isNotEmpty() == true -> search.price.toString() + " $"
                else -> "Price Not Found"
            }

            releaseDateTextView.text = search.releaseDate?.substring(0, 10)

            Glide
                .with(imageView.context)
                .load(search.artworkUrl100?.let { ImageResizer.getMediumImage(it) })
                .into(imageView)

            searchCardView.animation = AnimationUtils.loadAnimation(
                searchCardView.context,
                R.anim.fade_transition_animation
            )

            searchCardView.setOnClickListener {
                listener?.let {
                    listener?.onClick(search)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BaseResult>) {
        this.searchList = data
    }

    fun addListener(listener: IClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int = searchList.size

    inner class HomeViewHolder(val binding: ItemSearchCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}