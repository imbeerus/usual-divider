package com.lockwood.divider.extensions

import android.view.View

internal fun View.updateView() {
    invalidate()
    requestLayout()
}