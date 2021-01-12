package com.dowy.android.adapter.categories.nowplaying

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.adapter.categories.LoadMoreViewHolder
import com.dowy.android.model.movie.Movie
import com.dowy.android.model.tv.TvSeries
import com.dowy.android.utils.FOOTER_VIEW_TYPE
import com.dowy.android.utils.MOVIE_VIEW_TYPE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

class NowPlayingAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(NowPlayingDiffUtilCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MOVIE_VIEW_TYPE -> NowPlayingMovieViewHolder.from(parent)
            FOOTER_VIEW_TYPE -> LoadMoreViewHolder.from(parent)
            //SERIES_VIEW_TYPE -> NowPlayingSeriesViewHolder.from(parent)
            else -> throw ClassCastException("Unknown View Type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NowPlayingMovieViewHolder -> {
                val movie = getItem(position) as DataItem.NowPlayingMovieItem
                holder.bind(movie.nowPlayingMovie)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.NowPlayingMovieItem -> MOVIE_VIEW_TYPE
            is DataItem.Footer -> FOOTER_VIEW_TYPE
            else -> -1
        }
    }

    fun submitNowPlayingMovie(list: List<Movie>) {
        adapterScope.launch {
            val nowPlayingMoviesList = list.map {
                DataItem.NowPlayingMovieItem(it)
            }
            val listWithFooter = nowPlayingMoviesList + listOf(DataItem.Footer)
            withContext(Dispatchers.Main) {
                submitList(listWithFooter)
            }
        }

    }
}


class NowPlayingDiffUtilCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    data class NowPlayingMovieItem(val nowPlayingMovie: Movie) : DataItem() {
        override val id: Int
            get() = nowPlayingMovie.id

    }

    data class NowPlayingTvSeriesItem(val nowPlayingTvSeries: TvSeries) : DataItem() {
        override val id: Int
            get() = nowPlayingTvSeries.id

    }

    object Footer : DataItem() {
        override val id: Int
            get() = Int.MAX_VALUE
    }

    abstract val id: Int
}