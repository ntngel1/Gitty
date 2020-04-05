/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.authorization.is_authorized

import io.reactivex.Single

interface IsAuthorizedInteractor {
    operator fun invoke(): Single<Boolean>
}