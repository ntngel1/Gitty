/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.user.get_current_user_login

import com.github.ntngel1.gitty.domain.gateways.UserGateway
import io.reactivex.Single
import javax.inject.Inject

class GetCurrentUserLoginInteractorImp @Inject constructor(
    private val userGateway: UserGateway
) : GetCurrentUserLoginInteractor {

    override fun invoke(): Single<String> =
        userGateway.getCurrentUserLogin()
}