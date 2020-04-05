package com.github.ntngel1.gitty.presentation.base.recyclerview

import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.github.ntngel1.gitty.presentation.base.recyclerview.core.Item
import com.github.ntngel1.gitty.presentation.base.recyclerview.core.ItemAdapter

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