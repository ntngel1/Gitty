/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code.dialogs.ref_selection.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.Callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import kotlinx.android.synthetic.main.item_ref_selection_header.view.*

data class RefSelectionHeaderItem(
    override val id: String,
    val onBranchesClicked: Callback<Unit>,
    val onTagsClicked: Callback<Unit>,
    val selectedButton: Int
) : Item<RefSelectionHeaderItem>() {

    override val layoutId: Int
        get() = R.layout.item_ref_selection_header

    override fun bind(view: View) = with(view) {
        bindSelectedButton()
    }

    private fun View.bindSelectedButton() {
        // Due to raw implementation of MaterialButtonToggleGroup we cannot
        // use OnButtonCheckedListener so we're using simple click listeners and checking button
        // directly through 'selectedButton'

        val checkedButtonId = when (selectedButton) {
            SELECTED_BUTTON_BRANCHES -> button_ref_selection_header_branches.id
            SELECTED_BUTTON_TAGS -> button_ref_selection_header_tags.id
            else -> throw IllegalStateException("No such button for selectedButton: $selectedButton")
        }

        toggle_group_ref_selection_header.check(checkedButtonId)

        button_ref_selection_header_branches.setOnClickListener {
            onBranchesClicked.listener.invoke()
        }

        button_ref_selection_header_tags.setOnClickListener {
            onTagsClicked.listener.invoke()
        }
    }

    override fun recycle(view: View) = with(view) {
        super.recycle(view)
        button_ref_selection_header_branches.setOnClickListener(null)
        button_ref_selection_header_tags.setOnClickListener(null)
    }

    companion object {
        const val SELECTED_BUTTON_BRANCHES = 1
        const val SELECTED_BUTTON_TAGS = 2
    }
}