package com.doiliomatsinhe.mymovies.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.TvSeries
import timber.log.Timber

@BindingAdapter("moviePoster")
fun ImageView.setMoviePoster(item: Movie?) {

    item?.let {
        Glide.with(this.context).load(it.fullPosterPath).into(this)
        Timber.d("Photo URL = ${it.fullPosterPath}")
    }
}

@BindingAdapter("seriesPoster")
fun ImageView.setSeriesPoster(item: TvSeries?) {

    item?.let {
        Glide.with(this.context).load(it.fullPosterPath).into(this)
        Timber.d("Series Photo URL = ${it.fullPosterPath}")
    }
}