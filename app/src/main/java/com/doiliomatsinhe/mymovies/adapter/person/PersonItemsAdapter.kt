package com.doiliomatsinhe.mymovies.adapter.person

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.model.person.PersonMovieCast
import com.doiliomatsinhe.mymovies.model.person.PersonTvCast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val PERSON_MOVIE_VIEW_TYPE = 0
private const val PERSON_SERIES_VIEW_TYPE = 1

class PersonItemsAdapter :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(PersonDiffUtilCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PersonMovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieItem = getItem(position) as DataItem.PersonMovieItem
        (holder as PersonMovieViewHolder).bind(movieItem.personMovie)
    }

    fun submitPersonMovieList(list: List<PersonMovieCast>) {

        adapterScope.launch {

            val listOfMovies = list.map {
                DataItem.PersonMovieItem(it)
            }

            withContext(Dispatchers.Main) {
                submitList(listOfMovies)
            }
        }
    }

}

class PersonDiffUtilCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {

    data class PersonMovieItem(val personMovie: PersonMovieCast) : DataItem() {
        override val id: Int
            get() = personMovie.id

    }

    data class PersonSeriesItem(val personSeries: PersonTvCast) : DataItem() {
        override val id: Int
            get() = personSeries.id

    }

    abstract val id: Int
}