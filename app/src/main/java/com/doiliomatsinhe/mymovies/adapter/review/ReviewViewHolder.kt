package com.doiliomatsinhe.mymovies.adapter.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.ReviewItemBinding
import com.doiliomatsinhe.mymovies.model.MovieReview

class ReviewViewHolder(private val binding: ReviewItemBinding) :
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
        fun from(parent: ViewGroup): ReviewViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ReviewItemBinding.inflate(inflater, parent, false)

            return ReviewViewHolder(
                binding
            )
        }
    }

}