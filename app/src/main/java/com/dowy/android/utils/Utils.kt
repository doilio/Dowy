package com.dowy.android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import com.dowy.android.R


object Utils {

    fun dptoPx(context: Context, value: Int): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun isAppInstalled(context: Context, appName: String): Boolean {
        val packageManager = context.packageManager
        var installed = false
        val packages = packageManager.getInstalledPackages(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == appName) {
                installed = true
                break
            }
        }
        return installed
    }

    fun shareDetails(message: String, context: Context) {

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = TEXT_PLAIN
            putExtra(Intent.EXTRA_TEXT, message)
        }

        val shareIntent = Intent.createChooser(intent, context.getString(R.string.share_using))
        context.startActivity(shareIntent)

    }

    fun openTrailer(youtubeLink: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse(youtubeLink)
        }

        if (isAppInstalled(context, context.getString(R.string.youtube_app_name))) {
            intent.`package` = context.getString(R.string.youtube_app_name)
        }
        context.startActivity(intent)
    }

    fun openReview(reviewUrl: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(reviewUrl))
            .addCategory(Intent.CATEGORY_BROWSABLE)
        context.startActivity(intent)
    }


}