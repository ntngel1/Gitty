/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile_overview

import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProfileOverviewView : MvpView {

    @AddToEndSingle
    fun setOverview(overview: ProfileOverviewEntity)
}