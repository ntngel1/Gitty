/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token

import com.github.ntngel1.gitty.domain.gateways.AuthorizationGateway
import io.reactivex.Maybe
import javax.inject.Inject

class GetAccessTokenInteractorImp @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : GetAccessTokenInteractor {

    override fun invoke(): Maybe<String> {
        return authorizationGateway.getAccessToken()
    }
}