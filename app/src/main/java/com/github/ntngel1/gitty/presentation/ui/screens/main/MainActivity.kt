/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.main

import android.os.Bundle
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.di.Scopes
import com.github.ntngel1.gitty.presentation.ui.Screens
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val presenter by moxyPresenter {
        scope.getInstance(MainPresenter::class.java)
    }

    private val scope by lazy {
        Toothpick.openScope(Scopes.INTERACTOR_SCOPE)
    }

    private val navigator: Navigator = SupportAppNavigator(
        this,
        supportFragmentManager,
        R.id.main_fragment_container
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        scope.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun showAuthScreen() {
        router.newRootScreen(Screens.Auth)
    }

    override fun showDrawerScreen() {
        router.newRootScreen(Screens.DrawerFlow)
    }
}
