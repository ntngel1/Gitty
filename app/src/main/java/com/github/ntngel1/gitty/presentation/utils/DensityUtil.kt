package com.github.ntngel1.gitty.presentation.utils

import android.content.res.Resources

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()