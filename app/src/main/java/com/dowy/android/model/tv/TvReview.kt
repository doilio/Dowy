package com.dowy.android.model.tv

import com.google.gson.annotations.SerializedName

data class TvReview(

    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String
)