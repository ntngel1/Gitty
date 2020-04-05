/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.gateways

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface AuthorizationGateway {
    fun createAccessToken(
        code: String,
        state: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ): Single<String>

    fun saveAccessToken(accessToken: String): Completable
    fun getAccessToken(): Maybe<String>
}