package com.dowy.android.adapter.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.databinding.MovieCastItemBinding
import com.dowy.android.model.movie.MovieCast

class MovieCastViewHolder(private val binding: MovieCastItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: MovieCast,
        clickListener: CastClickListener
    ) {
        binding.cast = item
        binding.clicklistener = clickListener
        binding.executePendingBindings()

    }

    companion object {
        fun from(parent: ViewGroup): MovieCastViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MovieCastItemBinding.inflate(inflater, parent, false)
            return MovieCastViewHolder(
                binding
            )
        }
    }
}
