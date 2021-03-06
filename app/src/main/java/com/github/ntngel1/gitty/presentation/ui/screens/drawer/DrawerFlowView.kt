/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.drawer

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface DrawerFlowView : MvpView {

    @OneExecution
    fun showProfileScreen(userLogin: String)
}