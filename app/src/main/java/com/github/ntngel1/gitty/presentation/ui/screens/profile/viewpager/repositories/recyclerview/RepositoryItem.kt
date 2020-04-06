/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories.recyclerview

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.core.Item
import com.github.ntngel1.gitty.presentation.utils.gone
import com.github.ntngel1.gitty.presentation.utils.quantityString
import com.github.ntngel1.gitty.presentation.utils.string
import com.github.ntngel1.gitty.presentation.utils.visible
import kotlinx.android.synthetic.main.item_repository.view.*
import org.threeten.bp.Instant

data class RepositoryItem(
    override val id: String,
    val name: String,
    val forkedFromRepositoryName: String?,
    val forkedFromRepositoryOwner: String?,
    val description: String?,
    val languageName: String?,
    val languageColor: String?,
    val licenseName: String?,
    val updatedAt: Instant?,
    val starsCount: Int,
    val forksCount: Int
) : Item<RepositoryItem>() {

    override val layoutId: Int
        get() = R.layout.item_repository

    override fun bind(view: View) = with(view) {
        bindName()
        bindForkedFrom()
        bindDescription()
        bindLanguageChip()
        bindUpdatedDateChip()
        bindStarsChip()
        bindForksChip()
        bindLicenseChip()
    }

    private fun View.bindForksChip() {
        if (forksCount == 0) {
            chip_repository_forks.gone()
            return
        }

        chip_repository_forks.visible()
        chip_repository_forks.text = if (forksCount <= FORKS_COUNT_THRESHOLD) {
            quantityString(R.plurals.forks_count, forksCount, forksCount)
        } else {
            string(R.string.forks_truncated, FORKS_COUNT_THRESHOLD)
        }
    }

    private fun View.bindStarsChip() {
        if (starsCount == 0) {
            chip_repository_stars.gone()
            return
        }

        chip_repository_stars.visible()
        chip_repository_stars.text = if (starsCount <= STARS_COUNT_THRESHOLD) {
            quantityString(R.plurals.stars_count, starsCount, starsCount)
        } else {
            string(R.string.stars_truncated, STARS_COUNT_THRESHOLD)
        }
    }

    private fun View.bindUpdatedDateChip() {
        if (updatedAt == null) {
            chip_repository_update_date.gone()
            return
        }

        chip_repository_update_date.visible()
        chip_repository_update_date.text = "SOMETHING" // TODO Date format
    }

    private fun View.bindLicenseChip() {
        if (licenseName.isNullOrBlank()) {
            chip_repository_license.gone()
            return
        }

        chip_repository_license.visible()
        chip_repository_license.text = licenseName
    }

    private fun View.bindLanguageChip() {
        if (languageName.isNullOrBlank() || languageColor.isNullOrBlank()) {
            chip_repository_language.gone()
            return
        }

        chip_repository_language.visible()
        chip_repository_language.text = languageName
        chip_repository_language.chipIcon = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor(languageColor))
        }
    }

    private fun View.bindForkedFrom() {
        if (forkedFromRepositoryName.isNullOrBlank() || forkedFromRepositoryOwner.isNullOrBlank()) {
            textview_repository_forked_from.gone()
            return
        }

        textview_repository_forked_from.visible()
        textview_repository_forked_from.text = string(
            R.string.profile_repositories_forked_from,
            forkedFromRepositoryOwner,
            forkedFromRepositoryName
        )
    }

    private fun View.bindDescription() {
        if (description.isNullOrBlank()) {
            textview_repository_description.gone()
            return
        }

        textview_repository_description.visible()
        textview_repository_description.text = description
    }

    private fun View.bindName() {
        textview_repository_name.text = name
    }

    companion object {
        private const val STARS_COUNT_THRESHOLD = 999
        private const val FORKS_COUNT_THRESHOLD = 99
    }
}