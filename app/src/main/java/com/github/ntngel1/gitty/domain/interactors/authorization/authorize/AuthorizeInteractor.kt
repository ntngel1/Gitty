/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.authorization.authorize

import io.reactivex.Completable

interface AuthorizeInteractor {
    operator fun invoke(redirectUrl: String): Completable
}