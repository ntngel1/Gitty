/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.star_repository

import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import io.reactivex.Single
import javax.inject.Inject

class StarRepositoryInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway
) : StarRepositoryInteractor {

    override fun invoke(repositoryId: String): Single<StarRepositoryInteractor.StarResult> =
        repositoryGateway.starRepository(repositoryId)
}