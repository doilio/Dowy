package com.doiliomatsinhe.mymovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.MoviesLoadStateFooterViewItemBinding


class MoviesLoadStateViewHolder(
    private val binding: MoviesLoadStateFooterViewItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.loadstateButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.loadstateError.text = loadState.error.localizedMessage
        }
        binding.loadstateProgress.isVisible = loadState is LoadState.Loading
        binding.loadstateButton.isVisible = loadState !is LoadState.Loading
        binding.loadstateError.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): MoviesLoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MoviesLoadStateFooterViewItemBinding.inflate(inflater, parent, false)

            return MoviesLoadStateViewHolder(binding, retry)
        }
    }


}
