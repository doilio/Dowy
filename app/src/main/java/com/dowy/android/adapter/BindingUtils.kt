package com.dowy.android.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dowy.android.R
import com.dowy.android.model.movie.Movie
import com.dowy.android.model.movie.MovieCast
import com.dowy.android.model.movie.MovieReview
import com.dowy.android.model.movie.MovieTrailer
import com.dowy.android.model.person.PersonMovieCast
import com.dowy.android.model.person.PersonTvCast
import com.dowy.android.model.tv.TvCast
import com.dowy.android.model.tv.TvReview
import com.dowy.android.model.tv.TvSeries
import com.dowy.android.model.tv.TvTrailer
import java.lang.ClassCastException

@BindingAdapter("moviePoster")
fun ImageView.setMoviePoster(item: Movie?) {

    item?.let {
        Glide.with(this).load(it.fullPosterPath).error(R.drawable.no_image).into(this)
    }
}

@BindingAdapter("seriesPoster")
fun ImageView.setSeriesPoster(item: TvSeries?) {

    item?.let {
        Glide.with(this).load(it.fullPosterPath).error(R.drawable.no_image).into(this)
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
            if (item.content.length > 400) {
                this.text = "${item.content.substring(0, 400)}...\nClick to read more."
            } else {
                this.text = item.content
            }
        }
        is TvReview -> {
            if (item.content.length > 400) {
                this.text = "${item.content.substring(0, 400)}...\nClick to read more."
            } else {
                this.text = item.content
            }
        }
    }
}

@BindingAdapter("itemImage")
fun ImageView.setItemImage(item: Any?) {

    when (item) {
        is PersonMovieCast -> {
            Glide.with(this).load(item.fullProfilePath).into(this)
        }
        is PersonTvCast -> {
            Glide.with(this).load(item.fullProfilePath).into(this)
        }
        else -> throw ClassCastException("Unknown Class")
    }


}

@SuppressLint("SetTextI18n")
@BindingAdapter("characterName")
fun TextView.setCharacterName(item: Any?) {

    when (item) {
        is PersonMovieCast -> {
            if (item.character.isNotEmpty()) {
                this.text = "as\n${item.character}"
                this.visibility = View.VISIBLE
            } else {
                this.visibility = View.GONE
            }

        }
        is PersonTvCast -> {
            if (item.character.isNotEmpty()) {
                this.text = "as\n${item.character}"
                this.visibility = View.VISIBLE
            } else {
                this.visibility = View.GONE
            }
        }
        else -> throw ClassCastException("Unknown Class")
    }

}