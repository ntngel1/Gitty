package com.github.ntngel1.gitty.presentation.ui.screens.profile

import com.github.ntngel1.gitty.domain.entities.user.UserProfileEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProfileView : MvpView {

    @AddToEndSingle
    fun setProfileHeader(userProfile: UserProfileEntity)
}