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
fun ImageView.setCastImage(item: Any?) {

    when (item) {
        is MovieCast? -> {
            if (!item?.profile_path.isNullOrEmpty()) {
                Glide.with(this).load(item?.fullProfilePath).into(this)
            }

        }
        is TvCast? -> {
            if (!item?.profile_path.isNullOrEmpty()) {
                Glide.with(this).load(item?.fullProfilePath).into(this)
            }
        }
    }
}

@BindingAdapter("castName")
fun TextView.setCastName(item: Any?) {

    when (item) {
        is MovieCast? -> {
            this.text = item?.name
        }
        is TvCast? -> {
            this.text = item?.name
        }
    }
}

@BindingAdapter("trailerName")
fun TextView.setTrailerName(item: Any?) {

    when (item) {
        is MovieTrailer? -> text = item?.name
        is TvTrailer? -> text = item?.name
    }
}

@BindingAdapter("trailerImage")
fun ImageView.setTrailerImage(item: Any?) {

    when (item) {
        is MovieTrailer -> Glide.with(this).load(item.trailerImagePath).into(this)
        is TvTrailer -> Glide.with(this).load(item.trailerImagePath).into(this)
    }
}

@BindingAdapter("reviewerName")
fun TextView.setReviewerName(item: Any?) {

    when (item) {
        is MovieReview -> text = item.author
        is TvReview -> text = item.author
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("reviewerText")
fun TextView.setReviewerText(item: Any?) {

    when (item) {
        is MovieReview -> {
            if (item.content.length > 1000) {
                this.text = "${item.content.substring(0, 1000)}...\n\"click to read more\"."
            } else {
                this.text = item.content
            }
        }
        is TvReview -> {
            if (item.content.length > 1000) {
                this.text = "${item.content.substring(0, 1000)}...\n\"click to read more\"."
            } else {
                this.text = item.content
            }
        }
    }
}