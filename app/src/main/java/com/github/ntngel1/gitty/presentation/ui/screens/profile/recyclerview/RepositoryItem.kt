/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.recyclerview

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.Callback1
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import com.github.ntngel1.gitty.presentation.utils.gone
import com.github.ntngel1.gitty.presentation.utils.quantityString
import com.github.ntngel1.gitty.presentation.utils.string
import com.github.ntngel1.gitty.presentation.utils.visible
import kotlinx.android.synthetic.main.item_repository.view.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.temporal.ChronoUnit
import kotlin.math.roundToInt

data class RepositoryItem(
    override val id: String,
    val repositoryId: String,
    val name: String,
    val forkedFromRepositoryName: String?,
    val forkedFromRepositoryOwner: String?,
    val description: String?,
    val languageName: String?,
    val languageColor: String?,
    val licenseName: String?,
    val updatedAt: Instant?,
    val starsCount: Int,
    val forksCount: Int,
    val onClicked: Callback1<String, Unit>
) : Item<RepositoryItem>() {

    override val layoutId: Int
        get() = R.layout.item_repository

    override fun bind(view: View) = with(view) {
        bindName()
        bindForkedFrom()
        bindDescription()
        bindLanguageChip()
        bindUpdatedAtChip()
        bindStarsChip()
        bindForksChip()
        bindLicenseChip()
        bindOnClickListener()
    }

    override fun recycle(view: View) = with(view) {
        super.recycle(view)
        this.setOnClickListener(null)
    }

    private fun View.bindOnClickListener() {
        this.setOnClickListener {
            onClicked.listener.invoke(repositoryId)
        }
    }

    private fun View.bindForksChip() {
        if (forksCount == 0) {
            chip_repository_forks.gone()
            return
        }

        chip_repository_forks.visible()
        chip_repository_forks.text = if (forksCount <= FORKS_COUNT_THRESHOLD) {
            quantityString(R.plurals.forks, forksCount, forksCount)
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
            quantityString(R.plurals.stars, starsCount, starsCount)
        } else {
            string(R.string.stars_truncated, STARS_COUNT_THRESHOLD)
        }
    }

    private fun View.bindUpdatedAtChip() {
        if (updatedAt == null) {
            chip_repository_update_date.gone()
            return
        }

        val zoneId = ZoneId.systemDefault()
        val zonedUpdatedAt = updatedAt.atZone(zoneId)
        val diffInSeconds = zonedUpdatedAt.until(
            Instant.now().atZone(zoneId),
            ChronoUnit.SECONDS
        ).toInt()

        chip_repository_update_date.visible()
        // TODO Refactor
        chip_repository_update_date.text = when {
            diffInSeconds <= 10 -> string(R.string.updated_now)
            diffInSeconds < SECONDS_IN_MINUTE -> string(
                R.string.updated_ago,
                quantityString(
                    R.plurals.seconds,
                    diffInSeconds,
                    diffInSeconds
                )
            )
            diffInSeconds < SECONDS_IN_HOUR -> string(
                R.string.updated_ago,
                quantityString(
                    R.plurals.minutes,
                    (diffInSeconds / SECONDS_IN_MINUTE.toFloat()).roundToInt(),
                    (diffInSeconds / SECONDS_IN_MINUTE.toFloat()).roundToInt()
                )
            )
            diffInSeconds < SECONDS_IN_DAY -> string(
                R.string.updated_ago,
                quantityString(
                    R.plurals.hours,
                    (diffInSeconds / SECONDS_IN_HOUR.toFloat()).roundToInt(),
                    (diffInSeconds / SECONDS_IN_HOUR.toFloat()).roundToInt()
                )
            )
            diffInSeconds < SECONDS_IN_MONTH -> string(
                R.string.updated_ago,
                quantityString(
                    R.plurals.days,
                    (diffInSeconds / SECONDS_IN_DAY.toFloat()).roundToInt(),
                    (diffInSeconds / SECONDS_IN_DAY.toFloat()).roundToInt()
                )
            )
            else -> string(
                R.string.updated_on,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    .format(zonedUpdatedAt)
            )
        }
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
            R.string.forked_from,
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

        private const val SECONDS_IN_MINUTE = 60
        private const val SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60
        private const val SECONDS_IN_DAY = SECONDS_IN_HOUR * 24
        private const val SECONDS_IN_MONTH = SECONDS_IN_DAY * 30
    }
}