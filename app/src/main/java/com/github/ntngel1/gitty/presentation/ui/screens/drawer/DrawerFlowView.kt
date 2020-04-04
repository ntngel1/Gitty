package com.github.ntngel1.gitty.presentation.ui.screens.drawer

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface DrawerFlowView : MvpView {

    @OneExecution
    fun showProfileScreen(userLogin: String)
}