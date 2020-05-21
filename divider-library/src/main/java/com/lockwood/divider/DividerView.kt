package com.lockwood.divider

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.lockwood.divider.extensions.fetchAttrs
import com.lockwood.divider.extensions.updateView

class DividerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {

        private val TAG = DividerView::class.java.simpleName

        private const val DEFAULT_ORIENTATION = Orientation.HORIZONTAL
        private const val DEFAULT_COLOR = Color.DKGRAY
    }

    var dividerOrientation = DEFAULT_ORIENTATION
        set(value) {
            field = value
            updateView()
        }

    private val defaultSize
        get() = resources.getDimensionPixelSize(R.dimen.default_divider_size)

    init {
        fetchAttrs(context, R.styleable.DividerView, attrs) {
            dividerOrientation = getInt(R.styleable.DividerView_dividerOrientation, DEFAULT_ORIENTATION)
        }

        setBackgroundColor(DEFAULT_COLOR)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = measureWidthDimension(widthMeasureSpec)
        val measuredHeight = measureHeightDimension(heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun measureWidthDimension(widthMeasureSpec: Int): Int {
        val forceDividerWidth = dividerOrientation != Orientation.HORIZONTAL
        return measureDimension(widthMeasureSpec, forceDividerWidth)
    }

    private fun measureHeightDimension(heightMeasureSpec: Int): Int {
        val forceDividerHeight = dividerOrientation != Orientation.VERTICAL
        return measureDimension(heightMeasureSpec, forceDividerHeight)
    }

    private fun measureDimension(measureSpec: Int, forceDividerSize: Boolean): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else {
            if (forceDividerSize) {
                defaultSize
            } else {
                specSize
            }
        }
    }

}