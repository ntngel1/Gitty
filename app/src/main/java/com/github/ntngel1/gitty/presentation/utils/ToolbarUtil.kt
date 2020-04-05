/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import androidx.annotation.DrawableRes
import com.github.ntngel1.gitty.R
import com.google.android.material.appbar.MaterialToolbar

fun MaterialToolbar.setup(
    title: String,
    @DrawableRes backDrawableResId: Int = R.drawable.ic_arrow_back_white,
    onBackClicked: () -> Unit
) {
    setTitle(title)
    setNavigationIcon(backDrawableResId)
    setNavigationOnClickListener { onBackClicked.invoke() }
}