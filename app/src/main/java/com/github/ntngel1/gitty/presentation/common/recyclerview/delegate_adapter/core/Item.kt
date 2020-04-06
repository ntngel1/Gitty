/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core

import android.view.View
import androidx.annotation.LayoutRes

abstract class Item<T : Item<T>> {

    abstract val id: String

    @get:LayoutRes
    abstract val layoutId: Int

    val viewType: Int
        get() = layoutId

    abstract fun bind(view: View)

    open fun areContentsTheSame(previousItem: T) = equals(previousItem)

    open fun bind(previousItem: T, view: View) = bind(view)

    open fun recycle(view: View) {}
}