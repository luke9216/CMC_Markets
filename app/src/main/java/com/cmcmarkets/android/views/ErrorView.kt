package com.cmcmarkets.android.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.cmcmarkets.android.exercise.R

class ErrorView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.error, this)
    }
}