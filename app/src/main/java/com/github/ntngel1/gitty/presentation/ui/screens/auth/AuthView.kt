/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.auth

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface AuthView : MvpView {

    @AddToEndSingle
    fun setIntro()

    @AddToEndSingle
    fun setWebView(authorizationUrl: String)

    @AddToEndSingle
    fun setLoading()

    @OneExecution
    fun showMainScreen()

    @OneExecution
    fun showError(throwable: Throwable)
}