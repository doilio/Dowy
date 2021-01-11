package com.dowy.android.utils

import android.content.Context
import android.util.TypedValue


object Utils {

    fun dptoPx(context: Context, value: Int): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
    }

    fun isAppInstalled(context: Context, appName: String): Boolean {
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
}