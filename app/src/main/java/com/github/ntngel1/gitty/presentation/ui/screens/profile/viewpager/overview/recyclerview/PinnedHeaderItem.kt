/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item

data class PinnedHeaderItem(
    override val id: String = "pinned_header"
) : Item<PinnedHeaderItem>() {

    override val layoutId: Int
        get() = R.layout.item_pinned_header

    override fun bind(view: View) {}
}