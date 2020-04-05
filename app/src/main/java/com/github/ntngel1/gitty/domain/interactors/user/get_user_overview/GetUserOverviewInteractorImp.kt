/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.user.get_user_overview

import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import io.reactivex.Single
import javax.inject.Inject

class GetUserOverviewInteractorImp @Inject constructor(
    private val userGateway: UserGateway
) : GetUserOverviewInteractor {

    override fun invoke(login: String): Single<ProfileOverviewEntity> =
        userGateway.getUserOverview(login)
}