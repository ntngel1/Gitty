/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.user.get_user_profile

import com.github.ntngel1.gitty.domain.entities.user.UserProfileEntity
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import io.reactivex.Single
import javax.inject.Inject

class GetUserProfileInteractorImp @Inject constructor(
    private val userGateway: UserGateway
) : GetUserProfileInteractor {

    override fun invoke(login: String): Single<UserProfileEntity> =
        userGateway.getUserProfile(login)
}