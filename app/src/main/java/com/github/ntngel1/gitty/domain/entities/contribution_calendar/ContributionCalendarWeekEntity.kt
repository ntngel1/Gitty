/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.contribution_calendar

import org.threeten.bp.LocalDate

data class ContributionCalendarWeekEntity(
    val firstDay: LocalDate,
    val days: List<ContributionCalendarDayEntity>
)
