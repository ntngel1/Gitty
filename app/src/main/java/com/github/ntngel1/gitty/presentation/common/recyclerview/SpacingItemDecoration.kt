/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration : RecyclerView.ItemDecoration() {

    var spacings = SparseArrayCompat<Int>()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        spacings.get(position)?.let { spacing ->
            outRect.top = spacing
        }
    }
}