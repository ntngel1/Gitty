/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.recyclerview.item_decorations

import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter

fun RecyclerView.ItemDecoration.itemForView(view: View, recyclerView: RecyclerView): Item<*> {
    val position = recyclerView.getChildAdapterPosition(view)
    return (recyclerView.adapter as ItemAdapter).items[position]
}

fun RecyclerView.ItemDecoration.viewById(id: String, recyclerView: RecyclerView) =
    recyclerView.children
        .first { view ->
            val item = itemForView(view, recyclerView)
            item.id == id
        }

fun RecyclerView.ItemDecoration.viewByIdOrNull(id: String, recyclerView: RecyclerView) =
    recyclerView.children
        .find { view ->
            val item = itemForView(view, recyclerView)
            item.id == id
        }