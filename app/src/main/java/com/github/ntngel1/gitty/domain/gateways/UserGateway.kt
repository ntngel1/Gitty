/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.gateways

import com.github.ntngel1.gitty.domain.entities.user.OverviewEntity
import com.github.ntngel1.gitty.domain.entities.user.ProfileEntity
import com.github.ntngel1.gitty.domain.entities.user.RepositoriesPageEntity
import io.reactivex.Single

interface UserGateway {
    fun getCurrentUserLogin(): Single<String>
    fun getProfile(login: String): Single<ProfileEntity>
    fun getOverview(login: String): Single<OverviewEntity>
    fun getRepositories(login: String, limit: Int, cursor: String?): Single<RepositoriesPageEntity>
}