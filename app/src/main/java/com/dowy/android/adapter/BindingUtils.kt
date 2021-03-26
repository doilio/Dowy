package com.dowy.android.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dowy.android.R

@BindingAdapter("poster")
fun ImageView.setPoster(fullPosterPath: String?) {

    fullPosterPath?.let {
        Glide.with(this).load(it).error(R.drawable.no_image).into(this)
    }
}

@BindingAdapter("loadImage")
fun ImageView.setImage(image: String?) {

    image?.let {
        Glide.with(this).load(it).into(this)
    }
}

@BindingAdapter("itemImage")
fun ImageView.setItemImage(fullProfilePath: String?) {

    fullProfilePath?.let {
        Glide.with(this).load(it).error(R.drawable.no_image_portrait1).into(this)
    }
}

@BindingAdapter("reviewerText")
fun TextView.setReviewerText(reviewContent: String?) {

    reviewContent?.let {
        text = if (it.length > 400) "${it.substring(0, 400)}...\nClick to read more." else it
    }
}

@BindingAdapter("characterName")
fun TextView.setCharacterName(character: String?) {

    character?.let {
        text = if (it.isNotEmpty()) "as\n${it}" else "Not Specified"
    }
}

@BindingAdapter("overviewText")
fun TextView.setOverView(msg: String?) {

    msg?.let {
        text = if (it.isNotEmpty()) it else context.getString(R.string.overview_error)
    }
}

@BindingAdapter("biographyText")
fun TextView.setBiography(msg: String?) {

    msg?.let {
        text = if (it.isNotEmpty()) it else context.getString(R.string.biography_is_currently_not_available)
    }
}