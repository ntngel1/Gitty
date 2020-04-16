/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_header

import com.github.ntngel1.gitty.domain.entities.repository.RepositoryHeaderEntity
import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import io.reactivex.Single
import javax.inject.Inject

class GetRepositoryHeaderInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway
) : GetRepositoryHeaderInteractor {

    override fun invoke(id: String): Single<RepositoryHeaderEntity> =
        repositoryGateway.getRepositoryHeader(id)
}