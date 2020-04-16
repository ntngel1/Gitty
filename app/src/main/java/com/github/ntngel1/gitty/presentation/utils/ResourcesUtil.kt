/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

//region Context

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

fun Context.drawable(
    @DrawableRes drawableResId: Int
) = ContextCompat.getDrawable(this, drawableResId)

fun Context.color(
    @ColorRes colorResId: Int
) = ContextCompat.getColor(this, colorResId)

fun Context.colorStateList(
    @ColorRes colorResId: Int
) = ColorStateList.valueOf(color(colorResId))

//endregion

//region View

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

fun View.drawable(
    @DrawableRes drawableResId: Int
) = context!!.drawable(drawableResId)

fun View.color(
    @ColorRes colorResId: Int
) = context!!.color(colorResId)

fun View.colorStateList(
    @ColorRes colorResId: Int
) = context!!.colorStateList(colorResId)

//endregion