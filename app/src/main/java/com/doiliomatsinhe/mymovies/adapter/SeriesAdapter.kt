package com.doiliomatsinhe.mymovies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.model.TvSeries

class SeriesAdapter(private val clickListener: SeriesClickListener) :
    ListAdapter<TvSeries, RecyclerView.ViewHolder>(SeriesDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SeriesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SeriesViewHolder).bind(getItem(position), clickListener)
    }

}

class SeriesDiffUtilCallback : DiffUtil.ItemCallback<TvSeries>() {
    override fun areItemsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
        return oldItem == newItem
    }

}

class SeriesClickListener(val clickListener: (tvSeries: TvSeries) -> Unit) {
    fun onClick(tvSeries: TvSeries) = clickListener(tvSeries)
}