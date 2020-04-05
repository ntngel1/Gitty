/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token

import io.reactivex.Maybe

interface GetAccessTokenInteractor {
    operator fun invoke(): Maybe<String>
}