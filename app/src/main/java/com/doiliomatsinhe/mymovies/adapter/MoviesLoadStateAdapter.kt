package com.doiliomatsinhe.mymovies.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class MoviesLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MoviesLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MoviesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MoviesLoadStateViewHolder {
        return MoviesLoadStateViewHolder.from(parent, retry)
    }

}