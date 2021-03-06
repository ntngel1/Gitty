/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.authorization.get_authorization_url

import io.reactivex.Single

interface GetAuthorizationUrlInteractor {
    operator fun invoke(): Single<String>
}