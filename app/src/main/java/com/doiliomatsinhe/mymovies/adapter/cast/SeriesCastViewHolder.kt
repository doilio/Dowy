package com.doiliomatsinhe.mymovies.adapter.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.SeriesCastItemBinding
import com.doiliomatsinhe.mymovies.model.TvCast

class SeriesCastViewHolder(private val binding: SeriesCastItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TvCast) {
        binding.cast = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SeriesCastViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = SeriesCastItemBinding.inflate(inflater, parent, false)
            return SeriesCastViewHolder(binding)
        }
    }

}