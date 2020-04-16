/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * SwipeRefreshLayout has a bug: when you're setting [SwipeRefreshLayout.isRefreshing] property
 * and then hiding SwipeRefreshLayout, progress indicator of SwipeRefreshLayout will not hide.
 * https://stackoverflow.com/a/54003459/8921745
 */
var SwipeRefreshLayout.isRefreshingSafe: Boolean
    get() = isRefreshing
    set(value) {
        if (value != isRefreshing) {
            post {
                isRefreshing = value
            }
        }
    }