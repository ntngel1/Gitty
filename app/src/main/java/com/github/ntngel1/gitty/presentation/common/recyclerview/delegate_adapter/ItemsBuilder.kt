/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter

import androidx.collection.SparseArrayCompat
import androidx.collection.set
import androidx.recyclerview.widget.RecyclerView
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.item_decorations.DividerItemDecoration
import com.github.ntngel1.gitty.presentation.common.recyclerview.item_decorations.SpacingItemDecoration
import java.util.*
import kotlin.collections.LinkedHashSet

@DslMarker
annotation class ItemsDsl

@ItemsDsl
class ItemsBuilder(
    private val dividerItemDecoration: DividerItemDecoration? = null,
    private val spacingItemDecoration: SpacingItemDecoration? = null
) {

    private val items = LinkedList<Item<*>>()
    private val dividerPositions = LinkedHashSet<Int>()
    private val spacings = SparseArrayCompat<Int>()

    fun item(block: () -> Item<*>?) {
        block.invoke()
            ?.let(items::add)
    }

    fun addItem(item: Item<*>?) {
        item?.let(items::add)
    }

    fun Item<*>.render() {
        items.add(this)
    }

    fun List<Item<*>>.render(spacingPx: Int = 0, withDivider: Boolean = false) {
        this.forEach { item ->
            if (withDivider) {
                divider()
            }

            spacing(spacingPx)
            items.add(item)
        }
    }

    fun divider() {
        checkNotNull(dividerItemDecoration)
        dividerPositions.add(items.size)
    }

    fun spacing(px: Int) {
        if (px <= 0) {
            return
        }

        checkNotNull(spacingItemDecoration)

        if (spacings.containsKey(items.size)) {
            spacings[items.size] = spacings.get(items.size)!! + px
        } else {
            spacings[items.size] = px
        }
    }

    fun build(): List<Item<*>> {
        val items = this.items.toList()

        dividerItemDecoration?.itemsPositions = dividerPositions
        spacingItemDecoration?.spacings = spacings

        return items
    }
}

fun RecyclerView.render(
    dividerItemDecoration: DividerItemDecoration? = null,
    spacingItemDecoration: SpacingItemDecoration? = null,
    builder: ItemsBuilder.() -> Unit
) {
    val adapter = adapter as ItemAdapter

    adapter.items = ItemsBuilder(dividerItemDecoration, spacingItemDecoration)
        .apply(builder)
        .build()
}