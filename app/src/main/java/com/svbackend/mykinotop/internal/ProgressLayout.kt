package com.svbackend.mykinotop.internal

import android.view.View
import android.widget.FrameLayout

fun showLoading(progressOverlay: FrameLayout) {
    progressOverlay.visibility = View.VISIBLE
}

fun hideLoading(progressOverlay: FrameLayout) {
    progressOverlay.visibility = View.INVISIBLE
}