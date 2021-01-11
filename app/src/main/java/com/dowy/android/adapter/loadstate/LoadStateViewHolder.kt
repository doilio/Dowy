package com.dowy.android.adapter.loadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.databinding.LoadStateFooterViewItemBinding


class LoadStateViewHolder(
    private val binding: LoadStateFooterViewItemBinding,
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
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadStateFooterViewItemBinding.inflate(inflater, parent, false)

            return LoadStateViewHolder(
                binding,
                retry
            )
        }
    }


}
