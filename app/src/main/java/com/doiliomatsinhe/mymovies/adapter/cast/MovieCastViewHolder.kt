package com.doiliomatsinhe.mymovies.adapter.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.MovieCastItemBinding
import com.doiliomatsinhe.mymovies.model.MovieCast

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
