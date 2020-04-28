/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_tree

import com.github.ntngel1.gitty.domain.entities.repository.FileTreeEntry
import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import io.reactivex.Single
import javax.inject.Inject

class GetRepositoryTreeInteractorImp @Inject constructor(
    private val repositoryGateway: RepositoryGateway
) : GetRepositoryTreeInteractor {

    override fun invoke(
        repositoryId: String,
        branchName: String,
        path: List<String>
    ): Single<List<FileTreeEntry>> {
        val stringPath = path.joinToString("/")

        return repositoryGateway.getRepositoryTree(
            id = repositoryId,
            branchName = branchName,
            path = stringPath
        )
    }
}