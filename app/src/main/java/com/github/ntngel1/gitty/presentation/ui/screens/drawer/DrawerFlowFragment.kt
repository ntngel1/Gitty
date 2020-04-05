/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.drawer

import android.os.Bundle
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.base.BaseFragment
import com.github.ntngel1.gitty.presentation.di.modules.DrawerModule
import com.github.ntngel1.gitty.presentation.ui.Screens
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.Scope
import javax.inject.Inject

class DrawerFlowFragment : BaseFragment(), DrawerFlowView {

    override val layoutId: Int
        get() = R.layout.fragment_drawer

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val presenter by moxyPresenter {
        scope.getInstance(DrawerFlowPresenter::class.java)
    }

    private val navigator by lazy {
        SupportAppNavigator(activity!!, childFragmentManager, R.id.drawer_fragment_container)
    }

    override fun initScope(scope: Scope) {
        super.initScope(scope)
        scope.installModules(DrawerModule())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.inject(this)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun showProfileScreen(userLogin: String) {
        router.newRootScreen(Screens.Profile(userLogin))
    }
}