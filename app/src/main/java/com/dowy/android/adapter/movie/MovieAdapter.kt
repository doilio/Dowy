package com.dowy.android.adapter.movie

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.model.movie.Movie
import com.dowy.android.utils.DATA_VIEWTYPE
import com.dowy.android.utils.LOADSTATE_VIEW_TYPE

class MovieAdapter(private val clickListener: MovieClickListener) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(
        MovieDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position), clickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            DATA_VIEWTYPE
        } else {
            LOADSTATE_VIEW_TYPE
        }
    }

}

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

class MovieClickListener(val clickListener: (movie: Movie) -> Unit) {

    fun onClick(movie: Movie) = clickListener(movie)
}