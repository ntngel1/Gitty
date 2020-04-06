/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.user.get_user_repositories

import com.github.ntngel1.gitty.domain.entities.user.RepositoriesPageEntity
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import io.reactivex.Single
import javax.inject.Inject

class GetUserRepositoriesInteractorImp @Inject constructor(
    private val userGateway: UserGateway
) : GetUserRepositoriesInteractor {

    override fun invoke(login: String, limit: Int, cursor: String): Single<RepositoriesPageEntity> =
        userGateway.getRepositories(login = login, limit = limit, cursor = cursor)
}