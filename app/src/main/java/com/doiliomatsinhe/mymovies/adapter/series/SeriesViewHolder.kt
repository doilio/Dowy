package com.doiliomatsinhe.mymovies.adapter.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.SeriesItemBinding
import com.doiliomatsinhe.mymovies.model.TvSeries

class SeriesViewHolder(private val binding: SeriesItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TvSeries?, clickListener: SeriesClickListener) {
        binding.series = item
        binding.clickListener = clickListener
        binding.executePendingBindings()

    }

    companion object {
        fun from(parent: ViewGroup): SeriesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = SeriesItemBinding.inflate(inflater, parent, false)
            return SeriesViewHolder(
                binding
            )
        }
    }

}