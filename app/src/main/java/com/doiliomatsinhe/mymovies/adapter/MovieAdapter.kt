package com.doiliomatsinhe.mymovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doiliomatsinhe.mymovies.R
import com.doiliomatsinhe.mymovies.databinding.MovieItemBinding
import com.doiliomatsinhe.mymovies.model.Movie
import timber.log.Timber

class MovieAdapter(private val clickListener: MovieClickListener) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(
        MovieDiffUtilCallback()
    ) {

    class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.movie_image)
        fun bind(
            item: Movie?,
            clickListener: MovieClickListener
        ) {
            item?.let {

                Glide.with(binding.root.context).load(item.fullPosterPath).into(binding.movieImage)
                Timber.d("Photo URL = ${item.fullPosterPath}")
            }
            binding.movie = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
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