package com.lockwood.divider.extensions

import android.content.Context
import android.util.DisplayMetrics

internal fun Context.dpToPx(dp: Number): Int {
    val densityDpi = resources.displayMetrics.densityDpi.toFloat()
    val densityScale = (densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    val dpValue = dp.toFloat()
    return (dpValue * densityScale).toInt()
}