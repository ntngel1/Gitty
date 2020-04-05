/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import android.view.View

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun List<View>.visibleOrGone(isVisible: Boolean) = forEach { view ->
    view.visibleOrGone(isVisible)
}

fun List<View>.visible() = forEach { view ->
    view.visible()
}

fun List<View>.gone() = forEach { view ->
    view.gone()
}