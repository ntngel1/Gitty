/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview

import com.github.ntngel1.gitty.domain.entities.user.OverviewEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProfileOverviewView : MvpView {

    @AddToEndSingle
    fun setOverview(overview: OverviewEntity)
}