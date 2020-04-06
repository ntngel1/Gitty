/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.core.Item
import kotlinx.android.synthetic.main.item_pinned_gist.view.*

data class PinnedGistItem(
    override val id: String,
    val name: String
) : Item<PinnedGistItem>() {

    override val layoutId: Int
        get() = R.layout.item_pinned_gist

    override fun bind(view: View) = with(view) {
        textview_pinned_gist_name.text = name
    }
}