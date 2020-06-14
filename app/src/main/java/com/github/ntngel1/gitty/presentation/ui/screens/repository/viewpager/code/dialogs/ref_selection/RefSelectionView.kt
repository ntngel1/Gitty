/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code.dialogs.ref_selection

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface RefSelectionView : MvpView {

    @AddToEndSingle
    fun setState(state: RefSelectionState)
}