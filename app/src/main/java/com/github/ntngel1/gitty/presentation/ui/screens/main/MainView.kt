/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.main

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface MainView : MvpView {

    @OneExecution
    fun showAuthScreen()

    @OneExecution
    fun showDrawerScreen()
}