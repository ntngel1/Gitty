package com.github.ntngel1.gitty.presentation.ui

import androidx.fragment.app.Fragment
import com.github.ntngel1.gitty.presentation.ui.screens.auth.AuthFragment
import com.github.ntngel1.gitty.presentation.ui.screens.drawer.DrawerFlowFragment
import com.github.ntngel1.gitty.presentation.ui.screens.profile.ProfileFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object Auth : SupportAppScreen() {
        override fun getFragment() = AuthFragment()
    }

    object DrawerFlow : SupportAppScreen() {
        override fun getFragment() = DrawerFlowFragment()
    }

    data class Profile(val userLogin: String) : SupportAppScreen() {
        override fun getFragment() = ProfileFragment.newInstance(userLogin)
    }
}