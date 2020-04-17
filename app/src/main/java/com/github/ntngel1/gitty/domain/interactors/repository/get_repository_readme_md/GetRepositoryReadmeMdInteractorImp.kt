/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_readme_md

import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import io.reactivex.Maybe
import javax.inject.Inject

class GetRepositoryReadmeMdInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway
) : GetRepositoryReadmeMdInteractor {

    override fun invoke(repositoryId: String, branchName: String): Maybe<String> {
        val uppercaseExpression = "$branchName:README.md"
        val lowercaseExpression = "$branchName:readme.md"

        return repositoryGateway.getRepositoryReadmeMd(
            id = repositoryId,
            uppercaseExpression = uppercaseExpression,
            lowercaseExpression = lowercaseExpression
        )
    }
}