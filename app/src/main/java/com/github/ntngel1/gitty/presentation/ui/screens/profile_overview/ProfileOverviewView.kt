package com.github.ntngel1.gitty.presentation.ui.screens.profile_overview

import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProfileOverviewView : MvpView {

    @AddToEndSingle
    fun setOverview(overview: ProfileOverviewEntity)
}