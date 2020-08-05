package com.doiliomatsinhe.mymovies.adapter.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.SeriesReviewItemBinding
import com.doiliomatsinhe.mymovies.model.TvReview

class SeriesReviewViewHolder(private val binding: SeriesReviewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TvReview?, clickListener: ReviewClickListener) {
        binding.review = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SeriesReviewViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = SeriesReviewItemBinding.inflate(inflater, parent, false)

            return SeriesReviewViewHolder(binding)
        }
    }
}