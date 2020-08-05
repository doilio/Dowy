package com.doiliomatsinhe.mymovies.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.doiliomatsinhe.mymovies.model.*

@BindingAdapter("moviePoster")
fun ImageView.setMoviePoster(item: Movie?) {

    item?.let {
        Glide.with(this).load(it.fullPosterPath).into(this)
    }
}

@BindingAdapter("seriesPoster")
fun ImageView.setSeriesPoster(item: TvSeries?) {

    item?.let {
        Glide.with(this).load(it.fullPosterPath).into(this)
    }
}

@BindingAdapter("castImage")
fun ImageView.setCastImage(item: MovieCast?) {

    item?.let {
        if (!it.profile_path.isNullOrEmpty()) {
            Glide.with(this).load(it.fullProfilePath).into(this)
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
        Glide.with(this).load(item.trailerImagePath).into(this)
    }
}

@BindingAdapter("reviewerName")
fun TextView.setReviewerName(item: MovieReview?) {
    item?.let {
        this.text = it.author
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("reviewerText")
fun TextView.setReviewerText(item: MovieReview?) {
    item?.let {
        if (it.content.length > 1000) {
            this.text = "${it.content.substring(0, 1000)}...\n\"click to read more\"."
        } else {
            this.text = it.content
        }

    }
}