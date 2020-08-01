package com.doiliomatsinhe.mymovies.adapter.trailer

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.model.MovieTrailer

class TrailerAdapter(private val clickListener: TrailerClickListener) :
    ListAdapter<MovieTrailer, RecyclerView.ViewHolder>(TrailerDiffUtilCallbak()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrailerViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TrailerViewHolder).bind(getItem(position),clickListener)
    }

}

class TrailerDiffUtilCallbak : DiffUtil.ItemCallback<MovieTrailer>() {
    override fun areItemsTheSame(oldItem: MovieTrailer, newItem: MovieTrailer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieTrailer, newItem: MovieTrailer): Boolean {
        return oldItem == newItem
    }

}

class TrailerClickListener(val clickListener: (trailer: MovieTrailer) -> Unit) {
    fun onCLick(trailer: MovieTrailer) = clickListener(trailer)
}