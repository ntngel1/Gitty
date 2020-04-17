/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.item_markdown.view.*

data class MarkdownItem(
    override val id: String,
    val markdownText: String,
    val markwon: Markwon
) : Item<MarkdownItem>() {

    override val layoutId: Int
        get() = R.layout.item_markdown

    override fun bind(view: View) = with(view) {
        markwon.setMarkdown(text_markdown, markdownText)
    }
}