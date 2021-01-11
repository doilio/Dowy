package com.dowy.android.adapter.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.databinding.SeriesReviewItemBinding
import com.dowy.android.model.tv.TvReview

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