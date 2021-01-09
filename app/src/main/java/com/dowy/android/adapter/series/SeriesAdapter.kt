package com.dowy.android.adapter.series

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.model.tv.TvSeries
import com.dowy.android.utils.DATA_VIEWTYPE
import com.dowy.android.utils.LOADSTATE_VIEW_TYPE

class SeriesAdapter(private val clickListener: SeriesClickListener) :
    PagingDataAdapter<TvSeries, RecyclerView.ViewHolder>(SeriesDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SeriesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SeriesViewHolder).bind(getItem(position), clickListener)
    }

    override fun getItemViewType(position: Int): Int {
       return if (position == itemCount) {
            DATA_VIEWTYPE
        } else {
            LOADSTATE_VIEW_TYPE
        }
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