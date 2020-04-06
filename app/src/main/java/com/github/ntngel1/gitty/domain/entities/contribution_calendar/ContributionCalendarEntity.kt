/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.contribution_calendar

data class ContributionCalendarEntity(
    val totalContributionCount: Int,
    val colors: List<String>,
    val weeks: List<ContributionCalendarWeekEntity>
)