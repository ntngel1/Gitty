/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.user

import com.github.ntngel1.gitty.domain.entities.contribution_calendar.ContributionCalendarEntity

data class ProfileOverviewEntity(
    val pinnedItems: List<PinnableItem>,
    val contributionCalendar: ContributionCalendarEntity
)