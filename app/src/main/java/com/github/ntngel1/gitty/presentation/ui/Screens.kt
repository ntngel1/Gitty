/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui

import com.github.ntngel1.gitty.presentation.ui.screens.auth.AuthFragment
import com.github.ntngel1.gitty.presentation.ui.screens.drawer.DrawerFlowFragment
import com.github.ntngel1.gitty.presentation.ui.screens.profile.ProfileFragment
import com.github.ntngel1.gitty.presentation.ui.screens.repository.RepositoryFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object Auth : SupportAppScreen() {
        override fun getFragment() = AuthFragment()
    }

    object DrawerFlow : SupportAppScreen() {
        override fun getFragment() = DrawerFlowFragment()
    }

    data class Profile(
        val userLogin: String
    ) : SupportAppScreen() {
        override fun getFragment() = ProfileFragment.newInstance(userLogin)
    }

    data class Repository(
        val repositoryName: String,
        val repositoryId: String
    ) : SupportAppScreen() {
        override fun getFragment() =
            RepositoryFragment.newInstance(
                repositoryName,
                repositoryId
            )
    }
}