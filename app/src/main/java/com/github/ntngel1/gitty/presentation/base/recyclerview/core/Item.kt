package com.github.ntngel1.gitty.presentation.base.recyclerview.core

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