package com.dowy.android.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.databinding.MovieItemBinding
import com.dowy.android.model.movie.Movie

class MovieViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie?, clickListener: MovieClickListener) {
        binding.movie = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MovieItemBinding.inflate(inflater, parent, false)
            return MovieViewHolder(
                binding
            )
        }
    }

}