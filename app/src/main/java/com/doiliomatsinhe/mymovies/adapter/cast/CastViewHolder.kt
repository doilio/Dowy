package com.doiliomatsinhe.mymovies.adapter.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.CastItemBinding
import com.doiliomatsinhe.mymovies.model.MovieCast

class CastViewHolder(private val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieCast) {
        binding.cast = item
        binding.executePendingBindings()

    }

    companion object {
        fun from(parent: ViewGroup): CastViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CastItemBinding.inflate(inflater, parent, false)
            return CastViewHolder(
                binding
            )
        }
    }
}
