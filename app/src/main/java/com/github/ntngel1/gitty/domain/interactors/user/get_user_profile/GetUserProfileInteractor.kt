/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.user.get_user_profile

import com.github.ntngel1.gitty.domain.entities.user.UserProfileEntity
import io.reactivex.Single

interface GetUserProfileInteractor {
    operator fun invoke(login: String): Single<UserProfileEntity>
}