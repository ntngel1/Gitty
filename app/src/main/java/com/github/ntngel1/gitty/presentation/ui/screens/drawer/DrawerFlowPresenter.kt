/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.drawer

import com.github.ntngel1.gitty.domain.interactors.user.get_current_user_login.GetCurrentUserLoginInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class DrawerFlowPresenter @Inject constructor(
    private val getCurrentUserLogin: GetCurrentUserLoginInteractor
) : BasePresenter<DrawerFlowView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showDefaultScreen()
    }

    private fun showDefaultScreen() {
        getCurrentUserLogin()
            .observeOn(AndroidSchedulers.mainThread())
            .logErrors()
            .subscribe({ currentUserLogin ->
                viewState.showProfileScreen(currentUserLogin)
            }, {
                // TODO Handle error
            })
            .disposeOnDestroy()
    }
}