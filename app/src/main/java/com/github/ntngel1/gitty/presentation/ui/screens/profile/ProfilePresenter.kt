/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile

import com.github.ntngel1.gitty.domain.interactors.user.get_user_profile.GetUserProfileInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.di.UserLogin
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    @UserLogin private val userLogin: String,
    private val getUserProfile: GetUserProfileInteractor
) : BasePresenter<ProfileView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUser()
    }

    private fun loadUser() {
        getUserProfile(userLogin)
            .logErrors()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userProfile ->
                viewState.setProfileHeader(userProfile)
            }, { throwable ->
                // TODO Handle error
            })
            .disposeOnDestroy()
    }
}