package com.doiliomatsinhe.mymovies.adapter.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.MovieTrailersItemBinding
import com.doiliomatsinhe.mymovies.model.movie.MovieTrailer

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