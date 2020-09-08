package com.doiliomatsinhe.mymovies.adapter.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.databinding.PersonMovieItemBinding
import com.doiliomatsinhe.mymovies.model.person.PersonMovieCast

class PersonMovieViewHolder(private val binding: PersonMovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PersonMovieCast?) {
        binding.movie = item
    }

    companion object {
        fun from(parent: ViewGroup): PersonMovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PersonMovieItemBinding.inflate(inflater, parent, false)

            return PersonMovieViewHolder(binding)
        }
    }
}