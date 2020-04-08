/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories

import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.presentation.common.pagination.Pagination
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProfileRepositoriesView : MvpView {

    @AddToEndSingle
    fun setState(state: Pagination.State<RepositoryEntity>)
}