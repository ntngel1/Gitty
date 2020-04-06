/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.recyclerview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ntngel1.gitty.presentation.utils.dp


class DividerItemDecoration(
    @ColorInt
    private val color: Int = Color.BLACK
) : RecyclerView.ItemDecoration() {

    var itemsPositions = emptySet<Int>()

    private val dividerPaint = Paint().apply {
        color = this@DividerItemDecoration.color
        strokeWidth = DIVIDER_HEIGHT.toFloat()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        c.save()

        itemsPositions.forEach { itemPosition ->
            (parent.layoutManager as LinearLayoutManager)
                .findViewByPosition(itemPosition)
                ?.let { view ->
                    val y = (view.top - DIVIDER_MARGIN).toFloat()
                    c.drawLine(0f, y, parent.width.toFloat(), y, dividerPaint)
                }
        }

        c.restore()
    }

    companion object {
        private val DIVIDER_HEIGHT = 2.dp
        private val DIVIDER_MARGIN = 16.dp
    }
}