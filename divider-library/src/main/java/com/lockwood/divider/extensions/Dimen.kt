package com.lockwood.divider.extensions

import android.content.Context
import android.util.DisplayMetrics

private val Context.densityDpi
    get() = resources.displayMetrics.densityDpi.toFloat()

private val Context.densityScale
    get() = densityDpi / DisplayMetrics.DENSITY_DEFAULT

internal fun Context.dpToPx(dp: Number): Number {
    return dp.toFloat() * densityScale
}