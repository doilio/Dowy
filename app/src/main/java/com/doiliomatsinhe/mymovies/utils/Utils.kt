package com.doiliomatsinhe.mymovies.utils

import android.content.Context
import android.util.TypedValue


object Utils {

    fun dptoPx(context: Context, value: Int): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
    }
}