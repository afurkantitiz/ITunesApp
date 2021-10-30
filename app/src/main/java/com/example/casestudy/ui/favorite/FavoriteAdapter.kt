package com.example.casestudy.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.databinding.ItemFavoriteCardBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var favoriteList: List<BaseResult> = emptyList()
    private var listener: IUnFavoriteItem? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val binding = ItemFavoriteCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        val search = favoriteList[position]

        holder.binding.apply {
            favoriteCollectionName.text =
                if (search.collectionName?.isNotEmpty() == true) search.collectionName else search.trackName
            favoriteReleaseDate.text = search.releaseDate?.substring(0,10)

            Glide
                .with(favoriteImageView.context)
                .load(search.artworkUrl100)
                .into(favoriteImageView)

            favoriteCardView.setOnClickListener {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(search)
                it.findNavController().navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavorite(favorites: List<BaseResult>) {
        this.favoriteList = favorites
        notifyDataSetChanged()
    }

    fun unFavorite(position: Int) {
        listener?.let {
            listener?.unFavoriteItem(favoriteList[position], position)
        }
    }

    fun addListener(listener: IUnFavoriteItem) {
        this.listener = listener
    }

    override fun getItemCount(): Int = favoriteList.size

    inner class FavoriteViewHolder(val binding: ItemFavoriteCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}