/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_overview

import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_readme_md.GetRepositoryReadmeMdInteractor
import io.reactivex.Single
import javax.inject.Inject

class GetRepositoryOverviewInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway,
    private val getRepositoryReadmeMd: GetRepositoryReadmeMdInteractor
) : GetRepositoryOverviewInteractor {

    override fun invoke(id: String): Single<RepositoryOverviewEntity> =
        repositoryGateway.getRepositoryOverview(id)
            .flatMap { repositoryOverview ->
                getReadmeMdIfExists(id, repositoryOverview)
            }

    private fun getReadmeMdIfExists(
        id: String,
        repositoryOverview: RepositoryOverviewEntity
    ): Single<RepositoryOverviewEntity> =
        if (repositoryOverview.defaultBranchName != null) {
            getRepositoryReadmeMd(
                repositoryId = id,
                branchName = repositoryOverview.defaultBranchName
            )
                .map { readmeMdText ->
                    repositoryOverview.copy(readmeMarkdownText = readmeMdText)
                }
                .toSingle(repositoryOverview)
        } else {
            Single.just(repositoryOverview)
        }
}