/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile_overview.recyclerview

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.base.recyclerview.core.Item
import com.github.ntngel1.gitty.presentation.utils.gone
import com.github.ntngel1.gitty.presentation.utils.quantityString
import com.github.ntngel1.gitty.presentation.utils.string
import com.github.ntngel1.gitty.presentation.utils.visible
import kotlinx.android.synthetic.main.item_pinned_repository.view.*

data class PinnedRepositoryItem(
    override val id: String,
    val name: String,
    val description: String?,
    val languageName: String?,
    val languageColor: String?,
    val forksCount: Int
) : Item<PinnedRepositoryItem>() {

    override val layoutId: Int
        get() = R.layout.item_pinned_repository

    override fun bind(view: View) = with(view) {
        bindName()
        bindDescription()
        bindLanguageChip()
        bindForksChip()
    }

    private fun View.bindForksChip() {
        if (forksCount == 0) {
            chip_pinned_repository_forks.gone()
            return
        }

        chip_pinned_repository_forks.visible()
        chip_pinned_repository_forks.text = if (forksCount <= FORKS_COUNT_THRESHOLD) {
            quantityString(R.plurals.forks_count, forksCount, forksCount)
        } else {
            string(R.string.forks_truncated, FORKS_COUNT_THRESHOLD)
        }
    }

    private fun View.bindName() {
        textview_pinned_repository_name.text = name
    }

    private fun View.bindLanguageChip() {
        if (languageName.isNullOrBlank() || languageColor.isNullOrBlank()) {
            chip_pinned_repository_language.gone()
            return
        }

        chip_pinned_repository_language.visible()
        chip_pinned_repository_language.text = languageName
        chip_pinned_repository_language.chipIcon = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor(languageColor))
        }
    }

    private fun View.bindDescription() {
        if (!description.isNullOrBlank()) {
            textview_pinned_repository_description.visible()
            textview_pinned_repository_description.text = description
        } else {
            textview_pinned_repository_description.gone()
        }
    }

    companion object {
        private const val FORKS_COUNT_THRESHOLD = 99
    }
}