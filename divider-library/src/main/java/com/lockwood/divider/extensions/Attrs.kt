package com.lockwood.divider.extensions

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes

internal inline fun fetchAttrs(
    context: Context,
    attrs: IntArray,
    set: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    fetch: TypedArray .() -> Unit = {}
) {
    val typedArray = context.theme.obtainStyledAttributes(
        set,
        attrs,
        defStyleAttr,
        defStyleRes
    )
    with(typedArray) {
        try {
            fetch()
        } finally {
            recycle()
        }
    }
}

internal inline fun fetchAndroidAttrs(
    context: Context,
    vararg attrs: Int,
    set: AttributeSet? = null,
    fetch: TypedArray .() -> Unit = {}
) {
    val typedArray = context.obtainStyledAttributes(set, attrs)
    with(typedArray) {
        try {
            fetch()
        } finally {
            recycle()
        }
    }
}