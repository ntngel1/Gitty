/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.gateways

import com.github.ntngel1.gitty.domain.entities.common.Page
import com.github.ntngel1.gitty.domain.entities.user.ProfileHeaderEntity
import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import com.github.ntngel1.gitty.domain.entities.user.UserRepositoryEntity
import io.reactivex.Single

interface UserGateway {
    fun getCurrentUserLogin(): Single<String>
    fun getProfileHeader(login: String): Single<ProfileHeaderEntity>
    fun getProfileOverview(login: String): Single<ProfileOverviewEntity>

    fun getRepositories(
        login: String,
        limit: Int,
        cursor: String?
    ): Single<Page<UserRepositoryEntity>>

    fun getStarredRepositories(
        login: String,
        limit: Int,
        cursor: String?
    ): Single<Page<UserRepositoryEntity>>
}