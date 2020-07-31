package com.doiliomatsinhe.mymovies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.model.MovieCast

class CastAdapter : ListAdapter<MovieCast, RecyclerView.ViewHolder>(CastDiffUtillCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CastViewHolder).bind(getItem(position))
    }

}

class CastDiffUtillCallback : DiffUtil.ItemCallback<MovieCast>() {

    override fun areItemsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
        return oldItem == newItem
    }
}