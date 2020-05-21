package com.lockwood.divider

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.lockwood.divider.extensions.dpToPx
import com.lockwood.divider.extensions.fetchAndroidAttrs
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
        private const val DEFAULT_DIVIDER_SIZE_DP = 1
    }

    //region Divider Vars
    var dividerOrientation: Int = DEFAULT_ORIENTATION
        set(value) {
            field = value
            updateView()
        }

    var dividerColor: Int = DEFAULT_COLOR
        set(value) {
            field = value
            updateDividerView()
        }

    var dividerSize: Int = context.dpToPx(DEFAULT_DIVIDER_SIZE_DP)
        set(value) {
            field = value
            updateView()
        }
    //endregion

    init {
        fetchAndroidAttrs(context, android.R.attr.background, set = attrs) {
            //region Divider Color
            dividerColor = try {
                getColor(0, DEFAULT_COLOR)
            } catch (e: Resources.NotFoundException) {
                Log.e(TAG, "You should use valid color background resource for divider")
                DEFAULT_COLOR
            }
            //endregion
        }

        fetchAttrs(context, R.styleable.DividerView, set = attrs) {
            //region Divider Orientation
            dividerOrientation = getInt(
                R.styleable.DividerView_dividerOrientation,
                DEFAULT_ORIENTATION
            )
            //endregion

            //region Divider Size
            val sizeFromAttrs = getDimensionPixelSize(
                R.styleable.DividerView_dividerSize,
                context.dpToPx(DEFAULT_DIVIDER_SIZE_DP)
            )
            dividerSize = if (sizeFromAttrs > 0) {
                sizeFromAttrs
            } else {
                context.dpToPx(DEFAULT_DIVIDER_SIZE_DP)
            }
            //endregion
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = measureWidthDimension(widthMeasureSpec)
        val measuredHeight = measureHeightDimension(heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun updateDividerView() {
        setBackgroundColor(dividerColor)
        updateView()
    }

    //region Measure Dimension functions
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
                dividerSize
            } else {
                specSize
            }
        }
    }
    //endregion

}