/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_default_branch

import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import io.reactivex.Maybe
import javax.inject.Inject

class GetRepositoryDefaultBranchInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway
) : GetRepositoryDefaultBranchInteractor {

    override fun invoke(repositoryId: String): Maybe<String> =
        repositoryGateway.getRepositoryDefaultBranch(repositoryId)
}