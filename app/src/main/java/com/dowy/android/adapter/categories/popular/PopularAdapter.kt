package com.dowy.android.adapter.categories.popular

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

class PopularAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(PopularDiffUtilCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MOVIE_VIEW_TYPE -> PopularMovieViewHolder.from(parent)
            FOOTER_VIEW_TYPE -> LoadMoreViewHolder.from(parent)
            //SERIES_VIEW_TYPE -> popularSeriesViewHolder.from(parent)
            else -> throw ClassCastException("Unknown View Type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PopularMovieViewHolder -> {
                val movie = getItem(position) as DataItem.PopularMovieItem
                holder.bind(movie.popularMovie)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.PopularMovieItem -> MOVIE_VIEW_TYPE
            is DataItem.Footer -> FOOTER_VIEW_TYPE
            else -> -1
        }
    }

    fun submitPopularMovie(list: List<Movie>) {
        adapterScope.launch {
            val popularMoviesList = list.map {
                DataItem.PopularMovieItem(it)
            }
            val listWithFooter = popularMoviesList + listOf(DataItem.Footer)
            withContext(Dispatchers.Main) {
                submitList(listWithFooter)
            }
        }

    }
}


class PopularDiffUtilCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    data class PopularMovieItem(val popularMovie: Movie) : DataItem() {
        override val id: Int
            get() = popularMovie.id

    }

    data class PopularTvSeriesItem(val popularTvSeries: TvSeries) : DataItem() {
        override val id: Int
            get() = popularTvSeries.id

    }

    object Footer : DataItem() {
        override val id: Int
            get() = Int.MAX_VALUE
    }

    abstract val id: Int
}