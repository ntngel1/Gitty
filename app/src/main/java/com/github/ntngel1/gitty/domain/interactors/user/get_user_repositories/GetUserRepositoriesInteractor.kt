/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.user.get_user_repositories

import com.github.ntngel1.gitty.domain.entities.common.Page
import com.github.ntngel1.gitty.domain.entities.user.UserRepositoryEntity
import io.reactivex.Single

interface GetUserRepositoriesInteractor {
    operator fun invoke(
        login: String,
        limit: Int,
        cursor: String?
    ): Single<Page<UserRepositoryEntity>>
}