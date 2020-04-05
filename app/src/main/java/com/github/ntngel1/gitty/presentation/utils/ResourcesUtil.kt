/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import android.content.Context
import android.view.View
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

fun Context.string(@StringRes stringResId: Int) = getString(stringResId)

fun Context.string(
    @StringRes stringResId: Int,
    vararg arguments: Any
) = getString(stringResId, *arguments)

fun Context.quantityString(
    @PluralsRes pluralsResId: Int,
    quantity: Int,
    vararg arguments: Any
) = resources.getQuantityString(pluralsResId, quantity, *arguments)

fun View.string(@StringRes stringResId: Int) = context!!.string(stringResId)

fun View.string(
    @StringRes stringResId: Int,
    vararg arguments: Any
) = context!!.string(stringResId, *arguments)

fun View.quantityString(
    @PluralsRes pluralsResId: Int,
    quantity: Int,
    vararg arguments: Any
) = context!!.quantityString(pluralsResId, quantity, *arguments)