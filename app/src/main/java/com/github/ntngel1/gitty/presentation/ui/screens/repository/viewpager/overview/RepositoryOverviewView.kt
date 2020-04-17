/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview

import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.presentation.common.single_load.SingleLoad
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface RepositoryOverviewView : MvpView {

    @AddToEndSingle
    fun setState(state: SingleLoad.State<RepositoryOverviewEntity>)

    @OneExecution
    fun showRepositoryScreen(repositoryName: String, repositoryId: String)

    @OneExecution
    fun showProfileScreen(ownerName: String)
}