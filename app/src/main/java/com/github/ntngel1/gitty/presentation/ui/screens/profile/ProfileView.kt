/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile

import com.github.ntngel1.gitty.domain.entities.user.ProfileHeaderEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProfileView : MvpView {

    @AddToEndSingle
    fun setProfileHeader(profileHeader: ProfileHeaderEntity)
}