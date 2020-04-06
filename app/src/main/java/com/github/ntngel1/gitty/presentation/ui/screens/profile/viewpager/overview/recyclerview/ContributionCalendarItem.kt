/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.contribution_calendar.ContributionCalendarEntity
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import kotlinx.android.synthetic.main.item_contribution_calendar.view.*

data class ContributionCalendarItem(
    override val id: String,
    val contributionCalendar: ContributionCalendarEntity
) : Item<ContributionCalendarItem>() {

    override val layoutId: Int
        get() = R.layout.item_contribution_calendar

    override fun bind(view: View) = with(view) {
        contribution_calendar_profile_overview.contributionCalendar = contributionCalendar
    }
}