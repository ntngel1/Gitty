/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.unstar_repository

import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import io.reactivex.Single
import javax.inject.Inject

class UnstarRepositoryInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway
) : UnstarRepositoryInteractor {

    override fun invoke(repositoryId: String): Single<UnstarRepositoryInteractor.UnstarResult> =
        repositoryGateway.unstarRepository(repositoryId)
}