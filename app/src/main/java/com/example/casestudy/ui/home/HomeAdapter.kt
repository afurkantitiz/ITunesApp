package com.example.casestudy.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.casestudy.data.entity.BaseResult
import com.example.casestudy.databinding.ItemSearchCardBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var searchList: List<BaseResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding =
            ItemSearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val search: BaseResult = searchList[position]

        holder.binding.nameTextView.text = search.artistName
        Glide
            .with(holder.binding.imageView.context)
            .load(search.artworkUrl100)
            .into(holder.binding.imageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BaseResult>) {
        this.searchList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = searchList.size

    inner class HomeViewHolder(val binding: ItemSearchCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}