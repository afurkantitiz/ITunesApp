package com.example.casestudy.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.casestudy.data.entity.PopularSearch
import com.example.casestudy.databinding.ItemPopularSearchCardBinding

class PopularSearchAdapter : RecyclerView.Adapter<PopularSearchAdapter.PopularViewHolder>() {
    private var popularList: List<PopularSearch> = emptyList()
    private var listener: IPopularSearch? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularSearchAdapter.PopularViewHolder {
        val binding =
            ItemPopularSearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val popular = popularList[position]

        holder.binding.apply {
            popularSearchName.text = popular.name

            popularSearchItem.setOnClickListener {
                listener?.let {
                    listener?.getPopularSearch(popular.name)
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PopularSearch>) {
        this.popularList = data
    }

    fun addListener(listener: IPopularSearch) {
        this.listener = listener
    }

    override fun getItemCount(): Int = popularList.size

    inner class PopularViewHolder(val binding: ItemPopularSearchCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}