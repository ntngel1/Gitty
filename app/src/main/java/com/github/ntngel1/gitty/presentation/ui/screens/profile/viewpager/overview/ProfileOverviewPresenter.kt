/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview

import com.github.ntngel1.gitty.domain.interactors.user.get_user_overview.GetUserOverviewInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.di.UserLogin
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ProfileOverviewPresenter @Inject constructor(
    @UserLogin private val userLogin: String,
    private val getUserOverview: GetUserOverviewInteractor
) : BasePresenter<ProfileOverviewView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadOverview()
    }

    private fun loadOverview() {
        getUserOverview(userLogin)
            .observeOn(AndroidSchedulers.mainThread())
            .logErrors()
            .subscribeBy(
                onSuccess = { profileOverview ->
                    viewState.setOverview(profileOverview)
                },
                onError = {
                    // TODO Handle error
                }
            )
            .disposeOnDestroy()
    }
}