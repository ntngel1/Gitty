package com.github.ntngel1.gitty.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG)
        .show()
}