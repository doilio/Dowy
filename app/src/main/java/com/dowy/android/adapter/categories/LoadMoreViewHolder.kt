package com.dowy.android.adapter.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.databinding.LoadMoreItemBinding

class LoadMoreViewHolder(binding: LoadMoreItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): LoadMoreViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadMoreItemBinding.inflate(inflater, parent, false)

            return LoadMoreViewHolder(binding)
        }
    }
}