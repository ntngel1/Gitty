/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface RepositoryCodeView : MvpView {

    @AddToEndSingle
    fun setState(state: RepositoryCodeState)
}