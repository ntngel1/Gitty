/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.user.get_user_overview

import com.github.ntngel1.gitty.domain.entities.user.OverviewEntity
import io.reactivex.Single

interface GetUserOverviewInteractor {
    operator fun invoke(login: String): Single<OverviewEntity>
}