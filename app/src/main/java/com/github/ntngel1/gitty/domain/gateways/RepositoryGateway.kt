/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.gateways

import com.github.ntngel1.gitty.domain.entities.common.Page
import com.github.ntngel1.gitty.domain.entities.repository.FileTreeEntry
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryHeaderEntity
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.domain.interactors.repository.star_repository.StarRepositoryInteractor
import com.github.ntngel1.gitty.domain.interactors.repository.unstar_repository.UnstarRepositoryInteractor
import io.reactivex.Maybe
import io.reactivex.Single

interface RepositoryGateway {
    fun getRepositoryHeader(id: String): Single<RepositoryHeaderEntity>
    fun getRepositoryOverview(id: String): Single<RepositoryOverviewEntity>
    fun starRepository(id: String): Single<StarRepositoryInteractor.StarResult>
    fun unstarRepository(id: String): Single<UnstarRepositoryInteractor.UnstarResult>
    fun getRepositoryDefaultBranch(id: String): Maybe<String>

    fun getRepositoryRefs(
        id: String,
        limit: Int,
        cursor: String?,
        refPrefix: String
    ): Single<Page<String>>

    fun getRepositoryReadmeMd(
        id: String,
        lowercaseExpression: String,
        uppercaseExpression: String
    ): Maybe<String>

    fun getRepositoryTree(
        id: String,
        branchName: String,
        path: String
    ): Single<List<FileTreeEntry>>
}