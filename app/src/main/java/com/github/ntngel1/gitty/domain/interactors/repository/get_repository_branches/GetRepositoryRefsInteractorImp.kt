/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_branches

import com.github.ntngel1.gitty.domain.entities.common.Page
import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import io.reactivex.Single
import javax.inject.Inject

class GetRepositoryRefsInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway
) : GetRepositoryRefsInteractor {

    override fun invoke(
        repositoryId: String,
        limit: Int,
        cursor: String?,
        filter: Int
    ): Single<Page<String>> {
        val refPrefix = when (filter) {
            GetRepositoryRefsInteractor.FILTER_BRANCHES -> "refs/heads/"
            GetRepositoryRefsInteractor.FILTER_TAGS -> "refs/tags/"
            else -> throw IllegalStateException("No such refPrefix for filter: $filter")
        }

        return repositoryGateway.getRepositoryRefs(repositoryId, limit, cursor, refPrefix)
    }
}