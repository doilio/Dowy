package com.doiliomatsinhe.mymovies.adapter.person

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.model.person.PersonMovieCast

class PersonMoviesAdapter(val clickListener: PersonMovieClickListener) :
    ListAdapter<PersonMovieCast, RecyclerView.ViewHolder>(PersonMovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PersonMovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PersonMovieViewHolder).bind(getItem(position), clickListener)
    }

}

class PersonMovieDiffUtilCallback : DiffUtil.ItemCallback<PersonMovieCast>() {
    override fun areItemsTheSame(oldItem: PersonMovieCast, newItem: PersonMovieCast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonMovieCast, newItem: PersonMovieCast): Boolean {
        return oldItem == newItem
    }

}

class PersonMovieClickListener(private val clickListener: (movie: PersonMovieCast) -> Unit) {

    fun onClick(movie: PersonMovieCast) = clickListener(movie)
}