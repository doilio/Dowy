package com.dowy.android.adapter.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.databinding.MovieTrailersItemBinding
import com.dowy.android.model.movie.MovieTrailer

class MovieTrailerViewHolder(private val binding: MovieTrailersItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: MovieTrailer?,
        clickListener: TrailerClickListener
    ) {
        binding.trailer = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MovieTrailerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MovieTrailersItemBinding.inflate(inflater, parent, false)

            return MovieTrailerViewHolder(
                binding
            )
        }
    }

}