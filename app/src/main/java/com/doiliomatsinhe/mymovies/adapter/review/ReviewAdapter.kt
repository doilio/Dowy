package com.doiliomatsinhe.mymovies.adapter.review

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.model.MovieReview

class ReviewAdapter(private val clickListener: ReviewClickListener) :
    ListAdapter<MovieReview, RecyclerView.ViewHolder>(ReviewDiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReviewViewHolder).bind(getItem(position), clickListener)
    }
}

class ReviewDiffUtilItemCallback : DiffUtil.ItemCallback<MovieReview>() {
    override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
        return oldItem == newItem
    }

}

class ReviewClickListener(val clicklistener: (review: MovieReview) -> Unit) {
    fun onClick(review: MovieReview) = clicklistener(review)
}