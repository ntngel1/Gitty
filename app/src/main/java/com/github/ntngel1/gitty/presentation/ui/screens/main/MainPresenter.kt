/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.main

import com.github.ntngel1.gitty.domain.interactors.authorization.is_authorized.IsAuthorizedInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val isAuthorized: IsAuthorizedInteractor
) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        selectScreen()
    }

    private fun selectScreen() {
        isAuthorized()
            .observeOn(AndroidSchedulers.mainThread())
            .logErrors()
            .subscribeBy(
                onSuccess = { isAuthorized ->
                    if (isAuthorized) {
                        viewState.showDrawerScreen()
                    } else {
                        viewState.showAuthScreen()
                    }
                },
                onError = {
                    // Shouldn't pass here
                }
            )
            .disposeOnDestroy()
    }
}