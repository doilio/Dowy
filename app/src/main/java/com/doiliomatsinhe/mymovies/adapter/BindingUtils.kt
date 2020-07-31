package com.doiliomatsinhe.mymovies.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.MovieCast
import com.doiliomatsinhe.mymovies.model.MovieTrailer
import com.doiliomatsinhe.mymovies.model.TvSeries
import timber.log.Timber

@BindingAdapter("moviePoster")
fun ImageView.setMoviePoster(item: Movie?) {

    item?.let {
        Glide.with(this.context).load(it.fullPosterPath).into(this)
        Timber.d("Title: ${it.title}\n Profile: ${it.fullPosterPath}\n Cover: ${it.fullBackDropPath}")
    }
}

@BindingAdapter("seriesPoster")
fun ImageView.setSeriesPoster(item: TvSeries?) {

    item?.let {
        Glide.with(this.context).load(it.fullPosterPath).into(this)
    }
}

@BindingAdapter("castImage")
fun ImageView.setCastImage(item: MovieCast?) {

    item?.let {
        if (!it.profile_path.isNullOrEmpty()) {
            Glide.with(this.context).load(it.fullProfilePath).into(this)
        }

    }
}

@BindingAdapter("castName")
fun TextView.setCastName(item: MovieCast?) {

    item?.let {
        this.text = it.name
    }
}

@BindingAdapter("trailerName")
fun TextView.setTrailerName(item: MovieTrailer?) {

    item?.let {
        this.text = item.name
    }
}

@BindingAdapter("trailerImage")
fun ImageView.setTrailerImage(item: MovieTrailer?) {

    item?.let {
        Glide.with(this.context).load(item.trailerImagePath).into(this)
    }
}