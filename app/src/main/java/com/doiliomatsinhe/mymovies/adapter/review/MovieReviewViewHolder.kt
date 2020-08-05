package com.doiliomatsinhe.mymovies.adapter.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.MovieReviewItemBinding
import com.doiliomatsinhe.mymovies.model.MovieReview

class MovieReviewViewHolder(private val binding: MovieReviewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: MovieReview?,
        clickListener: ReviewClickListener
    ) {
        binding.review = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MovieReviewViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MovieReviewItemBinding.inflate(inflater, parent, false)

            return MovieReviewViewHolder(
                binding
            )
        }
    }

}