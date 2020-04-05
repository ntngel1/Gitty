/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.authorization.is_authorized

import com.github.ntngel1.gitty.domain.gateways.AuthorizationGateway
import io.reactivex.Single
import javax.inject.Inject

class IsAuthorizedInteractorImp @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : IsAuthorizedInteractor {

    override fun invoke(): Single<Boolean> =
        authorizationGateway.getAccessToken()
            .toSingle()
            .map { true }
            .onErrorReturn { false }
}